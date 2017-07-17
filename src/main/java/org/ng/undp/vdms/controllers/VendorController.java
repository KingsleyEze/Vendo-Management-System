package org.ng.undp.vdms.controllers;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.dao.Pager;
import org.ng.undp.vdms.dao.Param;
import org.ng.undp.vdms.domains.*;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.domains.security.Permission;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.repositories.CountryRepository;
import org.ng.undp.vdms.repositories.StateRepository;
import org.ng.undp.vdms.services.*;
import org.ng.undp.vdms.services.security.RoleService;
import org.ng.undp.vdms.utils.Auth;
import org.ng.undp.vdms.utils.ShortUUID;
import org.ng.undp.vdms.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.*;

/**
 * Created by macbook on 6/16/17.
 */

@Controller
@RequestMapping(value = "vendors")
public class VendorController extends BaseController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SmtpService smtpService;

    @Autowired
    private VendorService vendorservice;

    @Autowired
    private  ConsultantService consultantService;

    @Autowired
    private  NgoService ngoService;

    @Autowired
    private SupplierService supplierService;


    @Autowired
    private RoleService roleService;

    @Autowired
    private SkillService skillService;
    @Autowired
    VpaService vpaService;
    @Autowired
    private VssService vssService;
    @Autowired
    private JavaMailSender mailSender;


    @Autowired
    private MailContentBuilder mailContentBuilder;


    @RequestMapping(value = "delete/{uuid}", method = RequestMethod.GET)
    public String deleteVendor(@PathVariable String uuid) {
        vendorservice.deleteByUuid(uuid);

        return "redirect:/vendors?msg=User deleted successfully";
    }


    @RequestMapping(value = "view/{vendorType}", method = RequestMethod.GET)
    public String profile(@PathVariable String vendorType, Principal principal, HttpServletRequest request, Model model) {

        String vendorID = request.getParameter("vendorID");
        //Check for consultant if user is loggedin
        if (StringUtils.isNotBlank(vendorType) && vendorID == null) {
            if (vendorType.toUpperCase().equals("consultant".toUpperCase())) {
                Consultant consultant = Accessor.findOne(Consultant.class, Filter.get()
                        .field("user.username", SecurityContextHolder.getContext()
                                .getAuthentication().getName()));

                return "redirect:/consultants/page/" + consultant.getId();
            }


        }
        //else if UN Staff show consultant
        else   if (StringUtils.isNotBlank(vendorType) && vendorID != null) {
            if (vendorType.toUpperCase().equals("consultant".toUpperCase())) {
                Consultant consultant = consultantService.findByUserId(Long.parseLong(vendorID ));
                System.out.println("The vendor ID = " + vendorID);

                return "redirect:/consultants/page/" + consultant.getId();
            }


        }

            return "vendors/create";
    }


    @RequestMapping(method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        //System.out.println("getting list of vendors");
        Param p = Utility.getParam(request);


        //Get to the authenticated user to see if the user is an admin
        Optional<User> optional = Auth.INSTANCE.getAuth();

        User authUser = optional.get();

        String username = authUser.getUsername();

        //Get the user role ADMIN_ACCOUNT
        Role role = roleService.findByName("ADMIN_ACCOUNT");

        //Type of vendor
        String vendorType = request.getParameter("type");
        List<Vendor> list;
        if (vendorType == null) {
            list = Accessor.findList(Vendor.class, Filter.get().field("user.deleted_at").isNull().field("deleted_at").isNull().field("suspended", false), p);
        } else {
            list = Accessor.findList(Vendor.class, Filter.get().field("user.deleted_at").isNull().field("deleted_at").isNull().field("suspended", false).field("user.roles").contains(getUserRoleID(vendorType)), p);

        }

        //List<Vendor> list = Accessor.findList(Vendor.class, Filter.get().field("deleted_at").isNull(), p);

        //Get list of vendors with role ADMIN_ACCOUNT
        List<User> adminvendors = Accessor.findList(User.class, Filter.get().field("roles").contains(role.getId()));


        model.addAttribute("vendors", list);
        model.addAttribute("type", vendorType);
        System.out.println("Total vendors " + list.size());

        Long total = Accessor.count(Vendor.class, Filter.get());

        model.addAttribute("pager", new Pager(total, p.getPage(), p.getSize()));


        return "vendors/all";
    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String createUser(Model model
    ) {
        Vendor user = new Vendor();
        user.setUser(new User());
        user.setUuid(ShortUUID.shortUUID().toString());


        model.addAttribute("user", user);


        model.addAttribute("vendors", this.getVendors());


        return "vendors/create";
    }

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String submitForm(Model model, HttpServletRequest request
    ) throws Exception {
        Map<String, Object> map = new HashMap<>(1);
        Vendor vendor = new Vendor();
        try {

            String name, firstname, vendorCategory, lastname, email;

            name = request.getParameter("name");
            firstname = request.getParameter("firstname");
            lastname = request.getParameter("lastname");
            email = request.getParameter("email");
            vendorCategory = request.getParameter("vendorCategory");

            String[] vpas, vsses, skills;

            skills = request.getParameterValues("skill");
            vpas = request.getParameterValues("vpa");
            vsses = request.getParameterValues("vss");


            List<Long> skillIds = new ArrayList<Long>();
            if (skills.length > 0) {
                for (int i = 0; i < skills.length; i++) {
                    skillIds.add(Long.parseLong(skills[i]));

                }
                List<Skill> selectedSkills = skillService.findAllById(skillIds);
                vendor.setSkill(selectedSkills);
            }

            List<Long> vpaIds = new ArrayList<Long>();

            for (int i = 0; i < vpas.length; i++) {
                vpaIds.add(Long.parseLong(vpas[i]));

            }
            List<Vpa> selectedvpas = vpaService.findAllById(vpaIds);


            List<Long> vssIds = new ArrayList<Long>();

            for (int i = 0; i < vsses.length; i++) {
                vssIds.add(Long.parseLong(vsses[i]));

            }

            List<Vss> selectedvss = vssService.findAllById(vssIds);


            Set<Role> selectedRoles = new HashSet<Role>();
            selectedRoles.add(roleService.findByName(vendorCategory));
            selectedRoles.add(roleService.findByName("VENDOR"));


            //declare vendor , user
            User u = new User();
            u.setFirstname(firstname);
            u.setLastname(lastname);
            u.setEmail(email);
            u.setUsername(email);
            u.setRoles(selectedRoles);
            u.setPassword("12345");

            String secureToken = UUID.randomUUID().toString();
            u.setResetPasswordToken(secureToken);

            userService.save(u);


            vendor.setUser(u);
            if (StringUtils.isBlank(name.trim())) {
                vendor.setName(name);
            } else {
                name = firstname + " " + lastname;
            }
            if (selectedvpas.size() > 0) {
                vendor.setVpa(selectedvpas);
            }
            if (selectedvss.size() > 0) {
                vendor.setVss(selectedvss);
            }


            vendorservice.save(vendor);

            /* send email */
            String activationURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/accounts/activate").queryParam("_key", secureToken).build().toUriString();


            String responseMessage = "Account Activation email link has been sent to your mail box!";
            boolean emailSent = true;
            String redirectionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/").build().toUriString();
            model.addAttribute("responseMessage", responseMessage);
            model.addAttribute("emailSent", emailSent);
            model.addAttribute("redirectionUrl", redirectionUrl);

            final Context ctx = new Context();

            ctx.setVariable("username", u.getUsername());
            ctx.setVariable("homeURL", redirectionUrl);
            ctx.setVariable("activationURL", activationURL);
            String messageBody = smtpService.prepareThymeleafMailBody("welcome-activation", ctx);
            System.out.println("MessageBody: " + messageBody);

            smtpService.sendSmtpAsync(u.getEmail(), "Vendor Account Activation", messageBody, "", "");


            return "redirect:/vendors?type=" + request.getParameter("vendorCategory").toString().trim().toLowerCase();
        } catch (Exception e) {
            map.put("errors", e.getMessage().toString());

        }
        model.addAttribute("user", vendor);


        model.addAttribute("vendors", this.getVendors());
        return "vendors/create";
    }

    @RequestMapping(value = "{uuid}", method = RequestMethod.GET)
    public String showVendor(Model model,
                             @PathVariable("uuid") String uuid
    ) {
        Vendor user = Accessor.findOne(Vendor.class, "uuid", uuid);
        if (null == user) {
            return "redirect:/vendors/create";
        }
        Map<String, String> vendorRolesAsMap = this.getVendorRolesAsMap(user.getUser());


        model.addAttribute("vendorRoles", vendorRolesAsMap);
        model.addAttribute("user", user);
        model.addAttribute("edit", "edit");


        model.addAttribute("vendors", this.getVendors());

        return "vendors/detail";
    }

    @RequestMapping(value = "edit/{uuid}", method = RequestMethod.GET)
    public String showEditForm(Model model,
                               @PathVariable("uuid") String uuid
    ) {
        Vendor user = Accessor.findOne(Vendor.class, "uuid", uuid);
        if (null == user) {
            return "redirect:/vendors/create";
        }
        Map<String, String> vendorRolesAsMap = this.getVendorRolesAsMap(user.getUser());


        model.addAttribute("vendorRoles", vendorRolesAsMap);
        model.addAttribute("user", user);
        model.addAttribute("edit", "edit");


        model.addAttribute("vendors", this.getVendors());

        return "vendors/create";
    }

    @RequestMapping(value = "edit", method = RequestMethod.POST)
    public String submitEditForm(HttpServletRequest request, Model model) {

        String name, firstname, uuid, lastname, email;
        uuid = request.getParameter("uuid");
        if (null != uuid) {
            Vendor vendor = Accessor.findOne(Vendor.class, Filter.get().field("uuid", uuid));
            name = request.getParameter("name");

            vendor.setName(name);

            firstname = request.getParameter("firstname");
            lastname = request.getParameter("lastname");

            vendor.getUser().setFirstname(firstname);
            vendor.getUser().setLastname(lastname);

            vendorservice.save(vendor);


            return "redirect:/vendors?type=" + vendor.getVendorType().toLowerCase();

        } else {
            return "redirect:/vendors?type=consultant";
        }


    }


    @RequestMapping(value = "suspend/{uuid}", method = RequestMethod.GET)
    public String suspendVendor(Model model,
                                @PathVariable("uuid") String uuid
    ) {
        Vendor user = Accessor.findOne(Vendor.class, "uuid", uuid);
        if (null == user) {
            return "redirect:/vendors?type=" + user.getVendorType().toLowerCase();
        }

        user.setSuspended(true);
        user.getUser().setEnabled(false);
        user.getUser().setAccountNonLocked(false);
        vendorservice.save(user);
        userService.save(user.getUser());

        model.addAttribute("message", "Succesfully suspended vendor");

        return "redirect:/vendors?type=" + user.getVendorType().toLowerCase();
    }


    @RequestMapping(value = "activate/{uuid}", method = RequestMethod.GET)
    public String activateVendor(Model model,
                                 @PathVariable("uuid") String uuid
    ) {
        Vendor user = Accessor.findOne(Vendor.class, "uuid", uuid);
        if (null == user) {
            return "redirect:/vendors?type=" + user.getVendorType().toLowerCase();
        }

        user.setSuspended(false);
        user.getUser().setEmail(StringUtils.split(user.getUser().getEmail(), "-")[0]);
        user.getUser().setDeleted_at(null);
        user.getUser().setEnabled(true);
        user.getUser().setAccountNonLocked(true);
        vendorservice.save(user);
        userService.save(user.getUser());

        model.addAttribute("message", "Succesfully  reactivated vendor");

        return "redirect:/vendors?type=" + user.getVendorType().toLowerCase();
    }

    @RequestMapping(method = RequestMethod.GET, value = "suspended")
    public String suspended(HttpServletRequest request, Model model) {

        Param p = Utility.getParam(request);


        //Get to the authenticated user to see if the user is an admin
        Optional<User> optional = Auth.INSTANCE.getAuth();

        User authUser = optional.get();

        String username = authUser.getUsername();

        //Get the user role ADMIN_ACCOUNT
        Role role = roleService.findByName("ADMIN_ACCOUNT");

        //Type of vendor
        String vendorType = request.getParameter("type");


        List<Vendor> list = Accessor.findList(Vendor.class, Filter.get().field("suspended", true).field("deleted_at").isNull(), p);


        //List<Vendor> list = Accessor.findList(Vendor.class, Filter.get().field("deleted_at").isNull(), p);

        //Get list of vendors with role ADMIN_ACCOUNT
        List<User> adminvendors = Accessor.findList(User.class, Filter.get().field("roles").contains(role.getId()));


        model.addAttribute("vendors", list);

        System.out.println("Total vendors " + list.size());

        Long total = Accessor.count(Vendor.class, Filter.get());

        model.addAttribute("pager", new Pager(total, p.getPage(), p.getSize()));


        return "vendors/suspended";
    }


    public static Vendor updateVendor(Vendor userForm, Vendor oldUser) {
        if (StringUtils.isNotEmpty(userForm.getName())) {
            oldUser.setName(userForm.getName());
        }


        return oldUser;
    }

    private Long getUserRoleID(String role) {
        return roleService.findByName(role.trim().toUpperCase()).getId();
    }


    private Map<String, String> getVendors() {
        Map<String, String> vendors = new HashMap<>(1);

        vendors.put(UserType.CONSULTANT.toString(), UserType.CONSULTANT.toString());
        vendors.put(UserType.NGO.toString(), UserType.NGO.toString());
        vendors.put(UserType.SUPPLIER.toString(), UserType.SUPPLIER.toString());

        return vendors;
    }

    private List<Role> getVendorRoles() {
        List<Role> roles = Accessor.findList(Role.class, Filter.get().field("active", true));
        List<Role> vendors = new ArrayList<Role>();

        roles.forEach((role) -> {
            if (!role.getName().equals("VENDOR") && (!role.getName().equals("ADMIN_ACCOUNT")) && (!role.getName().equals("STAFF"))) {
                vendors.add(role);
            }


        });
        return vendors;


    }

    private Map<String, String> getVendorRolesAsMap(User user) {
        Set<Role> roles = Accessor.findOne(User.class, Filter.get().field("uuid", user.getUuid())).getRoles();
        Map<String, String> vendors = new HashMap<String, String>(1);

        roles.forEach((role) -> {
            if (!role.getName().equals("VENDOR") && (!role.getName().equals("ADMIN_ACCOUNT")) && (!role.getName().equals("STAFF"))) {
                vendors.put(role.getName().toUpperCase(), role.getName().toUpperCase());
            }


        });
        return vendors;

    }

    private Map<String, String> getVendorRolesAsMap() {
        List<Role> roles = Accessor.findList(Role.class, Filter.get().field("active", true));
        Map<String, String> vendors = new HashMap<String, String>(1);

        roles.forEach((role) -> {
            if (!role.getName().equals("VENDOR") && (!role.getName().equals("ADMIN_ACCOUNT")) && (!role.getName().equals("STAFF"))) {
                vendors.put(role.getName().toUpperCase(), role.getName().toUpperCase());
            }


        });
        return vendors;

    }


    private List<Role> getNonVendorRoles() {
        List<Role> roles = Accessor.findList(Role.class, Filter.get().field("active", true));
        List<Role> vendors = new ArrayList<Role>();

        roles.forEach((role) -> {
            if (!role.getName().equals("NGO") && (!role.getName().equals("CONSULTANT")) && (!role.getName().equals("SUPPLIER")) && (!role.getName().equals("VENDOR"))) {
                vendors.add(role);
            }


        });
        return vendors;
    }

}
