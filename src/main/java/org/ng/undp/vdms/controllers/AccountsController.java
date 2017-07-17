package org.ng.undp.vdms.controllers;

import com.google.gson.Gson;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;

import org.apache.xmlbeans.impl.xb.xmlconfig.Usertypeconfig;
import org.joda.time.DateTime;
import org.ng.undp.vdms.customannotations.Auditable;
import org.ng.undp.vdms.customannotations.constants.AuditingActionType;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.*;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.dto.ChangePasswordDto;
import org.ng.undp.vdms.dto.CustomUserDetails;
import org.ng.undp.vdms.services.*;
import org.ng.undp.vdms.utils.Auth;
import org.ng.undp.vdms.utils.DateUtils;
import org.ng.undp.vdms.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.jws.soap.SOAPBinding;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * Created by abdulhakim on 10/14/16.
 */

@Controller
@RequestMapping(value = "accounts")
public class AccountsController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserService userAccountsService;
    @Autowired
    private final VpaService vpaService;

    @Autowired
    private final VssService vssService;
    @Autowired
    private final SkillService skillService;
    @Autowired
    private final VendorService vendorService;


    @Autowired
    private SecurityService securityService;

    public String responseMessage = "org.springframework.mail.MailException;\n" +
            "import org.springframework.mail.MailSender;Invalid address mail.This account doesn't exist";

    @Autowired
    private JavaMailSender mailSender;

    public AccountsController(UserService us, VpaService vs, VssService vss, SkillService sk, VendorService ves) {

        this.userAccountsService = us;
        this.vpaService = vs;
        this.vssService = vss;
        this.skillService = sk;
        this.vendorService = ves;

    }

    @ModelAttribute
    public Model commonModels(Model model) throws Exception {


        Long noticeCount = Accessor.count(Notice.class, Filter.get().field("publish", true).field("active", true));
        model.addAttribute("noticeCount", noticeCount);

        Long noticeCountMonth = Accessor.count(Notice.class, Filter.get()
                .field("publish", true).field("active", true)
                .field("created_at").between(DateUtils.getMonthStartDate(new Date()), DateUtils.getMonthEndDate(new Date()))

        );
        model.addAttribute("noticeCountMonth", noticeCountMonth);
        Long noticeCountYear = Accessor.count(Notice.class, Filter.get().field("publish", true)
                .field("active", true)
                .field("created_at").between(DateUtils.getYearStartDate(new Date()), DateUtils.getYearEndDate(new Date()))
        );
        model.addAttribute("noticeCountYear", noticeCountYear);

        Long tenderCountMonth = Accessor.count(Tender.class, Filter.get().field("created_at")
                .between(DateUtils.getMonthStartDate(new Date()), DateUtils.getMonthEndDate(new Date())));
        model.addAttribute("tenderCountMonth", tenderCountMonth);
        Long tenderCountYear = Accessor.count(Tender.class, Filter.get()
                .field("created_at")
                .between(DateUtils.getYearStartDate(new Date()), DateUtils.getYearEndDate(new Date())));
        model.addAttribute("tenderCountYear", tenderCountYear);

        Map<String, String> vendors = new HashMap<>(1);


        vendors.put(UserType.CONSULTANT.toString(), UserType.CONSULTANT.toString());
        vendors.put(UserType.NGO.toString(), UserType.NGO.toString());
        vendors.put(UserType.SUPPLIER.toString(), UserType.SUPPLIER.toString());


        model.addAttribute("vendors", vendors);

        return model;


    }

    @RequestMapping(value = "activate", method = RequestMethod.GET)
    public String activateAccount(@RequestParam(value = "_key") String resetPasswordToken, final Model model) {
        System.out.println("You have reached the account activation url");

        User user = userAccountsService.getUserByResetPasswordToken(resetPasswordToken);
        if (user != null) {

            user.setEnabled(true);
            user.setResetPasswordToken(null);
            userAccountsService.save(user);
            success.add("User has now been activated");
            model.addAttribute("success", Utility.success(success));

            return "redirect:/accounts/login";
            //redirect to select vendortype, vpa and vss
        } else {
            String responseMessage = "Invalid activation link";
            errors.add(responseMessage);
            model.addAttribute("errors", Utility.errors(errors));


            return "redirect:/register";
        }


    }

    @RequestMapping(value = "completeSignup", method = RequestMethod.POST)
    public String completeVendorSignup(HttpServletRequest request, Principal principal, final Model model, RedirectAttributes redirectAttributes) throws IOException, ServletException {


        CustomUserDetails loggedinUser = this.loggedInUser(principal);


        if (loggedinUser.getUser().getRoleNames().contains("VENDOR") ||
                loggedinUser.getUser().getRoleNames().contains("SUPPLIER") ||
                loggedinUser.getUser().getRoleNames().contains("CONSULTANT") ||
                loggedinUser.getUser().getRoleNames().contains("NGO")) {
            Vendor thematicSize = vendorService.findOneByUser(loggedinUser.getUser());
            model.addAttribute("thematicSizze", thematicSize);

            Long tenderCount = Accessor.count(Tender.class, Filter.get().field("vendor", thematicSize));
            model.addAttribute("tenderCount", tenderCount);


        }
        String[] vCat = request.getParameterValues("vendorCategory");


        if (vCat == null || vCat.length == 0) {

            errors.add("All fields are required, Please select appropriately!");


            redirectAttributes.addFlashAttribute("errors", Utility.errors(errors));

            model.addAttribute("user", loggedinUser);

            /*
            stickyNotice.add("We are glad you signed up, you are yet to complete your profile. ");
            stickyNotice.add("STEP 1: Select Vendor Category ");
            stickyNotice.add("STEP 2: Answer all the questions on the next page");
            stickyNotice.add("STEP 3: Complete the Vendor Registration Form");
            stickyNotice.add("STEP 4: Upon completion of your profile, We will send you an email.");
            model.addAttribute("stickyNotice", stickyNotice);
           */

            return "accounts/index";
        }


        //Get selected VPAs
        String[] vpa = request.getParameterValues("vpa");

        if (vpa == null || vpa.length == 0) {
            model.addAttribute("user", loggedinUser);
            errors.add("All fields are required, Please select appropriately!");

           /* redirectAttributes.addFlashAttribute("errors", Utility.errors(errors));

            stickyNotice.add("We are glad you signed up, you are yet to complete your profile. ");
            stickyNotice.add("STEP 1: Select Vendor Category ");
            stickyNotice.add("STEP 2: Answer all the questions on the next page");
            stickyNotice.add("STEP 3: Complete the Vendor Registration Form");
            stickyNotice.add("STEP 4: Upon completion of your profile, We will send you an email.");
            model.addAttribute("stickyNotice", stickyNotice);
           */

            return "accounts/index";
        }


        List<Long> vpaIds = new ArrayList<Long>();


        for (int i = 0; i < vpa.length; i++) {
            vpaIds.add(Long.parseLong(vpa[i]));

        }
        ArrayList<Vendor> vendo = new ArrayList<Vendor>();

        List<Vpa> vpas = vpaService.findAllById(vpaIds);


        //Get selected VSS
        String[] vss = request.getParameterValues("vss");

        if (vss == null || vss.length == 0) {

            errors.add("All fields are required, Please select appropriately!");
            model.addAttribute("user", loggedinUser);

           /*
            redirectAttributes.addFlashAttribute("errors", Utility.errors(errors));


            stickyNotice.add("We are glad you signed up, you are yet to complete your profile. ");
            stickyNotice.add("STEP 1: Select Vendor Category ");
            stickyNotice.add("STEP 2: Answer all the questions on the next page");
            stickyNotice.add("STEP 3: Complete the Vendor Registration Form");
            stickyNotice.add("STEP 4: Upon completion of your profile, We will send you an email.");
            model.addAttribute("stickyNotice", stickyNotice);

            model.addAttribute("stickyNotice", stickyNotice);
            */
            return "accounts/index";
        }


        List<Long> vssIds = new ArrayList<Long>();

        for (int i = 0; i < vss.length; i++) {
            vssIds.add(Long.parseLong(vss[i]));

        }

        List<Vss> vsses = vssService.findAllById(vssIds);
        System.out.println("The selected VSS size is " + vsses.size());


        //Get Skills selected

        String[] skills = request.getParameterValues("skills");

        if (skills == null || skills.length == 0) {

            errors.add("All fields are required, Please select appropriately!");
            model.addAttribute("user", loggedinUser);

            /*
            redirectAttributes.addFlashAttribute("errors", Utility.errors(errors));


            stickyNotice.add("We are glad you signed up, you are yet to complete your profile. ");
            stickyNotice.add("STEP 1: Select Vendor Category ");
            stickyNotice.add("STEP 2: Answer all the questions on the next page");
            stickyNotice.add("STEP 3: Complete the Vendor Registration Form");
            stickyNotice.add("STEP 4: Upon completion of your profile, We will send you an email.");

            model.addAttribute("stickyNotice", stickyNotice);
            */
            return "accounts/index";
        }


        List<Long> skillIds = new ArrayList<Long>();

        for (int i = 0; i < skills.length; i++) {
            skillIds.add(Long.parseLong(skills[i]));

        }
        List<Skill> selectedSkills = skillService.findAllById(skillIds);
        System.out.println("The selected skill size is " + selectedSkills.size());


        String email = this.loggedInUser(principal).getUser().getEmail();
        User user = userAccountsService.getUserByEmail(email);

        String[] uType = new String[1];
        uType[0] = UserType.valueOf(vCat[0]).toString();


        Role userRole = roleService.findByName(vCat[0]);
        Role userRole1 = roleService.findByName("VENDOR");
        Set<Role> vendorRole = new HashSet<>(2);
        vendorRole.add(userRole);
        vendorRole.add(userRole1);

        user.setRoles(vendorRole);
        user.setUserTypes(uType);
        user = userAccountsService.save(user);


        final User theUser = user;
        Vendor vendor = vendorService.findOneByUser(theUser);
        if (vendor == null) {
            vendor = new Vendor();
        }
        //vendor.setName(theUser.getFirstname() + " " + theUser.getLastname());
        vendor.setVpa(vpas);
        vendor.setUser(theUser);

        vendor.setVss(vsses);
        vendor.setSkill(selectedSkills);


        vendorService.save(vendor);


        //Update the user roles in session
        userService.autoLogin(loggedinUser.getUsername(), request);

        String consultantAccountRole = UserType.CONSULTANT.toString();
        String ngoAdminAccountRole = UserType.NGO.toString();
        String supplierAccountRole = UserType.SUPPLIER.toString();
        String vendorAccountRole = UserType.VENDOR.toString();


        List<String> userRoles = new ArrayList<>();
        for (Role r : loggedinUser.getUser().getRoles()) {
            userRoles.add(r.getName());
        }



        this.redirectVendor(loggedinUser);
        return "redirect:/accounts/login";


    }


    /* Allow only Account Admin Access */
//@PreAuthorize("hasAuthority('ACCOUNT_ADMIN')")
    @RequestMapping(method = RequestMethod.GET)

    @Transactional
    public String getUserDashboard(Principal principal, Model model) throws Exception {


        CustomUserDetails loggedinUser = this.loggedInUser(principal);

        if (loggedinUser.getUser().getRoleNames().contains("VENDOR") ||
                loggedinUser.getUser().getRoleNames().contains("SUPPLIER") ||
                loggedinUser.getUser().getRoleNames().contains("CONSULTANT") ||
                loggedinUser.getUser().getRoleNames().contains("NGO")) {
            Vendor thematicSize = vendorService.findOneByUser(loggedinUser.getUser());
            model.addAttribute("thematicSizze", thematicSize);

            Long tenderCount = Accessor.count(Tender.class, Filter.get().field("vendor", thematicSize));
            model.addAttribute("tenderCount", tenderCount);


        } else {


            Set<Role> roles = new HashSet<>();
            Role vendorRole = roleService.findByName("VENDOR");
            roles.add(vendorRole);


            Set<Role> ngoRoles = new HashSet<>();
            Role ngoRole = roleService.findByName("NGO");
            ngoRoles.add(ngoRole);


            Set<Role> staffRoles = new HashSet<>();
            Role staffRole = roleService.findByName("STAFF");
            staffRoles.add(staffRole);


            String[] userTypes = new String[]{UserType.VENDOR.toString()};

            List<User> userList = Accessor.findList(User.class, Filter.get().field("deleted_at").isNull().field("roles").contains(vendorRole.getId()));


            int ngoNum = userService.findAllByRoles(ngoRoles).size();

            int staffNum = userService.findAllByRoles(staffRoles).size();

            int yearStaffNum = userService.findAllByRoles(staffRoles).size();
            int monthStaffNum = userService.findAllByRoles(staffRoles).size();

            Long contractsNum = Accessor.count(Contract.class, Filter.get());


            model.addAttribute("contractsNum", contractsNum);
            //String date = "1/1/" + new Date().getYear();
            //String endDate ="12/31/" + new Date().getYear();
            //Instant startOfDay = Instant.parse(date);
            //Instant endOfDay = Instant.parse(endDate);

            //Instant startOfDay = Instant.parse(date).truncatedTo(ChronoUnit.DAYS);
            //Instant endOfDay = startOfDay.plus(Duration.ofDays(1));

            Long activeContractsNum = 3L;
            //Accessor.count(Contract.class, Filter.get().field("contractDate").gte(endOfDay));
            model.addAttribute("activeContractsNum", activeContractsNum);

            Long yearContractsNum = 0L;
            // Accessor.count(Contract.class, Filter.get().field("created_at").between(startOfDay, endOfDay));

            model.addAttribute("yearContractsNum", yearContractsNum);


            model.addAttribute("vendorsNum", userList.size());

            model.addAttribute("ngoNum", ngoNum);
            model.addAttribute("staffNum", staffNum);


        }

        /* Authentication logic below*/


        List<Vendor> theVendorVpa = vendorService.findAllByUser(loggedinUser.getUser());

        String adminAccountRole = UserType.ADMIN_ACCOUNT.toString();
        String staffAccountRole = UserType.STAFF.toString();
        String UNStaffAccountRole = UserType.UNSTAFF.toString();

        String consultantAccountRole = UserType.CONSULTANT.toString();
        String ngoAdminAccountRole = UserType.NGO.toString();
        String supplierAccountRole = UserType.SUPPLIER.toString();
        String vendorAccountRole = UserType.VENDOR.toString();


        List<String> userRoles = new ArrayList<>();
        for (Role r : loggedinUser.getUser().getRoles()) {
            userRoles.add(r.getName());
        }

        userAccountsService.updateLastLoggedIn(LocalDateTime.now(), loggedinUser.getUsername());

        model.addAttribute("user", loggedinUser);

        if (loggedinUser.getUser().isEnabled() == false) {
            Map<String, Object> map = new HashMap<>(1);
            map.put("Message:", String.format("Sorry, %s %s, your account has not being activated. " +
                    "Follow the link sent to your email to activate your account.", loggedinUser.getUser().getLastname(), loggedinUser.getUser().getFirstname()));
            model.addAttribute("errors", map);

            //redirect to feedback page
            return "feedback";

        }

        model.addAttribute("user", loggedinUser);
       /* stickyNotice.add("We are glad you signed up, you are yet to complete your profile. ");
        stickyNotice.add("STEP 1: Select Vendor Category ");
        stickyNotice.add("STEP 2: Answer all the questions on the next page");
        stickyNotice.add("STEP 3: Complete the Vendor Registration Form");
        stickyNotice.add("STEP 4: Upon completion of your profile, We will send you an email.");
        */


        if (userRoles.contains(adminAccountRole)) {
            //redirect to admin dashboard
            return "accounts/admin/index";
        } else if (userRoles.size() >= 1 && (userRoles.contains(adminAccountRole) == false) && (userRoles.contains(staffAccountRole) == false) && (userRoles.contains(UNStaffAccountRole) == false
        ) && (theVendorVpa.size() == 0)) {

            //model.addAttribute("stickyNotice", Utility.stickyNotice(stickyNotice));

            //redirect to vendor account dashboard
            return "accounts/index";
        } else if (userRoles.contains(consultantAccountRole)) {
            //redirect to school admin account dashboard
            return "accounts/consultant/index";
        } else if (userRoles.contains(ngoAdminAccountRole)) {
            //redirect to ngo account dashboard
            return "accounts/ngo/index";
        } else if (userRoles.contains(supplierAccountRole)) {
            //redirect to ngo account dashboard
            return "accounts/supplier/index";
        } else if (userRoles.size() == 1 && userRoles.contains(vendorAccountRole)) {


            //  model.addAttribute("stickyNotice", Utility.stickyNotice(stickyNotice));
            //redirect to vendor account dashboard
            return "accounts/index";
        }

        return "accounts/index";


    }

    @RequestMapping(value = "logVendorIn", method = RequestMethod.GET)
    public String autologin(final Model model, @RequestParam(required = true) String username) {

        securityService.autologin(username);

        return "redirect:/accounts";
    }

    @Auditable(actionType = AuditingActionType.INTERNAL_USER_REGISTRATION)

    @RequestMapping(value = "login")
    public String login(HttpServletRequest request, Principal principal, Model model) {

        if (request.getParameter("error") != null) {

            model.addAttribute("error", request.getParameter("error"));
        }

        System.out.println(request.getParameter("error"));
        if (principal != null && ((Authentication) principal).isAuthenticated()) {
            return "redirect:/accounts";
        }

        return "accounts/signin";
    }


    @RequestMapping(value = "forgotpassword", method = RequestMethod.GET)
    public String forgetPasswordView(final Model model) {
        model.addAttribute("user", new User());
        return "/accounts/forgot-password";
    }

    @RequestMapping(value = "forgotpassword", method = RequestMethod.POST)
    public String forgetPassword(@ModelAttribute User user, final Model model) throws MessagingException, IOException {
        model.addAttribute("user", user);
        logger.info("UserTransmittedEmail# :" + user.getEmail());

        User foundUser = this.userAccountsService.getUserByEmail(user.getEmail());
        if (foundUser != null) {
            String secureToken = UUID.randomUUID().toString();
            foundUser.setResetPasswordToken(secureToken);
      /*
       Give token one hour expiration delay
      */
            Date currentDate = new Date();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(currentDate);
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            Date expirationDate = calendar.getTime();
            logger.info("Expriration date :" + expirationDate);
            foundUser.setResetPasswordExpires(expirationDate);

            String responseMessage = "";
            /*
      Update user into database
      */
            userAccountsService.save(foundUser);
            String text = "You are receiving this because you (or someone else) have requested the reset of the password for your account.\n\n"
                    + "Please click on the following link, or paste into your browser to complete the reset password process :\n\n"
                    + ServletUriComponentsBuilder.fromCurrentContextPath().path("/accounts/resetpassword").queryParam("_key", secureToken).build().toUriString()
                    + "\n\n If you did not request this, please ignore this email and your password will remain unchanged.";
            sendResetPasswordLink(foundUser.getEmail(), text);

            responseMessage = "Password reset email link has been sent to your mail box";
            boolean emailSent = true;
            String redirectionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/").build().toUriString();
            model.addAttribute("responseMessage", responseMessage);
            model.addAttribute("emailSent", emailSent);
            return "/accounts/forgot-password";
        }
        //responseMessage = "Bad email address";
        model.addAttribute("invalidMailAddress", responseMessage);

        return "accounts/forgot-password";
    }


    private void sendResetPasswordLink(String email, String text) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("no-reply@un.one.org");
        helper.setTo(email);
        helper.setText(text, true);
        helper.setSubject("Password reset request");
        mailSender.send(message);
    }


    @RequestMapping(value = "resetpassword", method = RequestMethod.GET)
    public String resetpasswordView(@RequestParam(value = "_key") String resetPasswordToken, final Model model) {
        User user = userAccountsService.getUserByResetPasswordToken(resetPasswordToken);
        String changePasswordButtonLabel = "Reset Password";
        Date expirationDate;
        if (user != null) {
            expirationDate = user.getResetPasswordExpires();
            if (expirationDate.after(new Date())) {
                /*model.addAttribute("user", user);
                model.addAttribute("resetPasswordToken", resetPasswordToken);
                model.addAttribute("changePasswordButtonLabel", changePasswordButtonLabel);*/
                return "redirect:/accounts/changeMyPassword?_key=" + resetPasswordToken;
            }
        }

        Map<String, String> map = new HashMap<>(1);
        map.put("error", "Could not find a valid user matching this request, or link has expired. ");
        model.addAttribute("errors", map);
        model.addAttribute("redirect", true);
        String redirectionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/").build().toUriString();
        model.addAttribute("redirectionUrl", redirectionUrl);
        return "feedback";

    }

    @RequestMapping(value = "resetpassword", method = RequestMethod.POST)
    public String resetPassword(HttpServletRequest request, @RequestParam(value = "_key") String resetPasswordToken, @ModelAttribute User user,
                                final Model model) {
        String uptadedPassword = request.getParameter("password");
        logger.info("UserLogin# " + user.getEmail() + "  UserPassword# " + uptadedPassword);
        User userToUpdate = userAccountsService.getUserByResetPasswordToken(resetPasswordToken);

        System.out.println("\n" + user.getPassword() + "\n");
        user.setPassword(uptadedPassword);

        userToUpdate.setResetPasswordToken(null);
        userToUpdate.setResetPasswordExpires(null);
        userAccountsService.save(userToUpdate);

        boolean passwordChanged = true;
        String redirectionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/").build().toUriString();
        String responseMessage = "Your password was successfully updated";
        model.addAttribute("passwordChanged", passwordChanged);
        model.addAttribute("redirectionUrl", redirectionUrl);
        model.addAttribute("responseMessage", responseMessage);
        return "accounts/reset-password";
    }


    @RequestMapping(value = "changeMyPassword", method = RequestMethod.GET)
    public String resetpasswordView(Model model, HttpServletRequest request, Principal principal) {
        ChangePasswordDto dto = new ChangePasswordDto();

        if (principal != null && ((Authentication) principal).isAuthenticated()) {
            model.addAttribute("loggedIn", true);
            model.addAttribute("loggedInUser", this.loggedInUser(principal));

        }
        model.addAttribute("passwordDto", dto);

        if (StringUtils.isNotBlank(request.getParameter(("_key")))) {
            model.addAttribute("resetPasswordToken", request.getParameter(("_key")));
        }
        return "accounts/reset-password";
    }


    @RequestMapping(value = "changeMyPassword", method = RequestMethod.POST)
    public String changeMyPassword(HttpServletRequest request, @Valid ChangePasswordDto dto, BindingResult bindingResult, Model model, RedirectAttributes mod) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", Utility.errors(bindingResult));
            model.addAttribute("passwordDto", dto);
            return "accounts/reset-password";
        }

        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            Map<String, String> map = new HashMap<>(1);
            map.put("message", "Please ensure that password field and Password confirmation field matches.");
            model.addAttribute("errors", map);
            model.addAttribute("passwordDto", dto);
            return "accounts/reset-password";
        }


        User user = getUserForPasswordChange(request);

        //if user is not loggedin and user is null
        if (null == user && StringUtils.isNotBlank(request.getParameter("resetPasswordToken"))) {
            Map<String, String> map = new HashMap<>(1);
            map.put("message", "Invalid user account, for password reset request.");
            model.addAttribute("errors", map);
            model.addAttribute("passwordDto", dto);
            return "accounts/reset-password";
        }

        if (user == null) {
            return "redirect:/accounts/login";
        }


        user.hashPassword(dto.getPassword());
        userAccountsService.save(user);


        model.addAttribute("success", true);
        model.addAttribute("message", "Password successfully changed.");
        success.add("Password successfully changed.");
        model.addAttribute("redirect", true);
        model.addAttribute("redirectionUrl", ServletUriComponentsBuilder.fromCurrentContextPath().path("/logout").build().toUriString());
        mod.addFlashAttribute("success", Utility.success(success));
        return "redirect:/accounts";
    }

    private User getUserForPasswordChange(HttpServletRequest request) {

        if (StringUtils.isNotBlank(request.getParameter("resetPasswordToken"))) {
            return userAccountsService.getUserByResetPasswordToken(request.getParameter("resetPasswordToken"));
        }

        Optional<User> optional = Auth.INSTANCE.getAuth();
        if (optional.isPresent()) {
            return optional.get();
        }

        return null;
    }


}




