package org.ng.undp.vdms.controllers;


import org.apache.commons.lang3.StringUtils;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.dao.Pager;
import org.ng.undp.vdms.dao.Param;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.security.Permission;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.repositories.CountryRepository;
import org.ng.undp.vdms.repositories.StateRepository;
import org.ng.undp.vdms.services.ImportService;
import org.ng.undp.vdms.services.MailContentBuilder;
import org.ng.undp.vdms.services.SmtpService;
import org.ng.undp.vdms.services.UserService;
import org.ng.undp.vdms.services.security.RoleService;
import org.ng.undp.vdms.utils.Auth;
import org.ng.undp.vdms.utils.ShortUUID;
import org.ng.undp.vdms.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
import java.util.*;


/**
 * Created by Samuel on 10/13/2016.
 */
@Controller
public class UsersController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private SmtpService smtpService;


    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CountryRepository countryRepository;


    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(UsersController.class);

    @Autowired
    private MailContentBuilder mailContentBuilder;

    public UsersController(UserService usrSvc) {
        this.userService = usrSvc;

    }

    private void sendAccountActivationLink(User user, String activationURL) throws MessagingException, IOException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("no-reply@rosters.ng.undp.org");
        helper.setTo(user.getEmail());
        // ...
        String name = user.getFirstname() + " " + user.getLastname();
        String content = mailContentBuilder.build(activationURL, name);
        //helper.setText(text, true);
        helper.setSubject("Vendor Database Account Activation Link");

        helper.setText(content, true);


        ;
        mailSender.send(message);
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String register(@ModelAttribute User user, Model model) {
        user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String processRegister(HttpServletRequest request, @ModelAttribute User user, Model model) throws MessagingException, IOException {


        String thePassword = request.getParameter("password");

        if (!request.getParameter("password").equals(request.getParameter("verify-password"))) {
            String responseMessage = "<br/>Passwords mismatch, Please enter the passwords again ";
            model.addAttribute("responseMessage", responseMessage);
            model.addAttribute("error", true);

            return "register";


        }

        if (userService.getUserByEmail(user.getEmail()) != null) {
            String responseMessage = "<br/>Email already taken!";
            boolean error = true;
            String redirectionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/register").build().toUriString();
            model.addAttribute("responseMessage", responseMessage);
            model.addAttribute("error", error);
            model.addAttribute("redirectionUrl", redirectionUrl);
            return "register";


        }
        user.setPassword(thePassword);
        //this.userService.save(user);
        // System.out.println("The Password is : " + thePassword);

        String secureToken = UUID.randomUUID().toString();
        user.setResetPasswordToken(secureToken);

        user.setUsername(user.getEmail());
        //  user.setPassword(thePassword);
        // List<String> roles = Arrays.asList(user.getUserTypes());
        user.setRoles(roleService.getDefaultRole("VENDOR"));

        user.setEnabled(false);

        this.userService.save(user);

        String activationURL = ServletUriComponentsBuilder.fromCurrentContextPath().path("/accounts/activate").queryParam("_key", secureToken).build().toUriString();


        String text = "";
        //sendAccountActivationLink(user, activationURL);

        String responseMessage = "<br/>Account Activation email link has been sent to your mail box!";
        boolean emailSent = true;
        String redirectionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/").build().toUriString();
        model.addAttribute("responseMessage", responseMessage);
        model.addAttribute("emailSent", emailSent);
        model.addAttribute("success", true);
        model.addAttribute("redirectionUrl", redirectionUrl);

        final Context ctx = new Context();

        ctx.setVariable("username", user.getUsername());
        ctx.setVariable("homeURL", redirectionUrl);
        ctx.setVariable("activationURL", activationURL);
        String messageBody = smtpService.prepareThymeleafMailBody("activation", ctx);
        System.out.println("MessageBody: " + messageBody);

        smtpService.sendSmtpAsync(user.getEmail(), "Account Activation", messageBody, "", "");




        return "register";
    }


    @RequestMapping(value = "/users/upload", method = RequestMethod.GET)
    public String getUploadUsers() {
        return "userupload";
    }

    @RequestMapping(value = "/users/upload", method = RequestMethod.POST)
    public String postUploadUsers(@RequestParam("file") MultipartFile file, @RequestParam("user_type") String userType) {
        String name = file.getOriginalFilename();
        if (name.contains(".xlsx") || name.contains(".xls")) {
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();

                    // Creating the directory to store file
                    String rootPath = System.getProperty("catalina.home");
                    File dir = new File(rootPath + File.separator + "tmpFiles");
                    if (!dir.exists())
                        dir.mkdirs();

                    // Create the file on server
                    File serverFile = new File(dir.getAbsolutePath()
                            + File.separator + name);
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();
                    if (serverFile.exists()) {
                        System.out.println("File was created");
                        System.out.println("Path: " + serverFile.getAbsolutePath());
                        String filepath = serverFile.getAbsolutePath();
                        ImportService importService = new ImportService(userRepository);
                        int result = importService.importUsers(filepath, userType);
                        if (result == 1 && !importService.hasError()) {
                            //worked successfully
                            return "redirect:/users/upload";
                        } else {
                            //didn't work successfully
                            return "redirect:/users/upload?error=upload_failed";
                        }
                    } else {
                        System.out.println("File was NOT created.");
                        return "redirect:/users/upload?error=file_error";
                    }

                } catch (Exception e) {
                    //return "You failed to upload " + name + " => " + e.getMessage();
                }
            } else {
                System.out.println("Error: File is empty");
            }
        } else {
            //invalid file format. redirect to the upload page with errors
            return "redirect:/users/upload?error=invalid_format";
        }
        return "redirect:/users/upload?error=upload_failed";
    }

    @RequestMapping(value = "/users/delete/{uuid}", method = RequestMethod.GET)
    public String deleteUser(@PathVariable String uuid, RedirectAttributes redirectAttributes) {
        userService.deleteUserByUuid(uuid);
        success.add("User deleted successfully");

        redirectAttributes.addFlashAttribute("success",Utility.success(success));


        return "redirect:/users?msg=User deleted successfully";
    }

    @RequestMapping(value = "/users/restore/{uuid}", method = RequestMethod.GET)
    public String restoreUser(@PathVariable String uuid) {
        User u = userService.getUserByUuid(uuid);
        u.setDeleted_at(null);
        if (null == u.getEmail()) {
            u.setEmail(StringUtils.split(u.getEmail(), "-")[0]);
        }
        userService.save(u);
        return "redirect:/users?msg=User restored successfully";
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String profile(Model model) {
        Optional<User> optional = Auth.INSTANCE.getAuth();
        if (!optional.isPresent()) {
            return "redirect:/accounts/login";
        }

        model.addAttribute("user", optional.get());
        model.addAttribute("edit", "edit");
        return "users/create";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public String index(HttpServletRequest request, Model model) {
        //System.out.println("getting list of users");
        Param p = Utility.getParam(request);

        //Get to the authenticated user to see if the user is an admin
        Optional<User> optional = Auth.INSTANCE.getAuth();

        User authUser = optional.get();

        String username = authUser.getUsername();

        //Get the user role ADMIN_ACCOUNT
        Role role = roleService.findByName("ADMIN_ACCOUNT");

       // List<User> list = Accessor.findList(User.class, Filter.get().field("deleted_at").isNull().field("username").ne("admin"), p);
        List<User> list = Accessor.findList(User.class, Filter.get().field("deleted_at").isNull(), p);

        //Get list of users with role ADMIN_ACCOUNT
        List<User> adminUsers = Accessor.findList(User.class, Filter.get().field("roles").contains(role.getId()));

        if (username != null && username.equals("admin")) {

            LOGGER.info("The User is an admin and should have the list of all users");

            model.addAttribute("users", list);

        } else {
            LOGGER.info("The User is not an admin thus should not have the list of all users");

            //Remove all the admins from the list
            adminUsers.forEach((u) -> {
                list.remove(u);

                model.addAttribute("users", list);
            });

            Long total = Accessor.count(User.class, Filter.get());

            model.addAttribute("pager", new Pager(total, p.getPage(), p.getSize()));

            return "users/all";

        }

        return "users/all";
    }


    @RequestMapping(value = "/users/deleted", method = RequestMethod.GET)
    public String deleted(HttpServletRequest request, Model model) {
        //System.out.println("getting list of users");
        Param p = Utility.getParam(request);

        //Get to the authenticated user to see if the user is an admin
        Optional<User> optional = Auth.INSTANCE.getAuth();

        User authUser = optional.get();

        String username = authUser.getUsername();

        //Get the user role ADMIN_ACCOUNT
        Role role = roleService.findByName("ADMIN_ACCOUNT");

        List<User> list = Accessor.findList(User.class, Filter.get().field("deleted_at").notNull(), p);

        //Get list of users with role ADMIN_ACCOUNT
        List<User> adminUsers = Accessor.findList(User.class, Filter.get().field("roles").contains(role.getId()));

        if (username != null && username.equals("admin")) {

            LOGGER.info("The User is an admin and should have the list of all users");

            model.addAttribute("users", list);

        } else {
            LOGGER.info("The User is not an admin thus should not have the list of all users");

            //Remove all the admins from the list
            adminUsers.forEach((u) -> {
                list.remove(u);

                model.addAttribute("users", list);
            });

            Long total = Accessor.count(User.class, Filter.get());

            model.addAttribute("pager", new Pager(total, p.getPage(), p.getSize()));

            return "users/deleted";

        }

        return "users/deleted";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
    @RequestMapping(value = "/users/create", method = RequestMethod.GET)
    public String createUser(Model model
    ) {
        User user = new User();
        user.setUuid(ShortUUID.shortUUID().toString());
        model.addAttribute("user", user);

        List<Role> roles = Accessor.findList(Role.class, Filter.get().field("active", true));
        List<Role> roles1 = new ArrayList<Role>();

        roles.forEach((role) -> {
            if (!role.getName().equals("CONSULTANT") && (!role.getName().equals("SUPPLIER")) && (!role.getName().equals("NGO"))) {
                roles1.add(role);
            }


        });

        model.addAttribute("roles", roles1);


        return "users/create";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
    @RequestMapping(value = "/users/create", method = RequestMethod.POST)
    public String submitForm(@Valid User user, BindingResult bindingResult,
                             Model model, HttpServletRequest request
    ) {


        if (bindingResult.hasErrors()) {
            System.out.println(Utility.errors(bindingResult));
            model.addAttribute("errors", Utility.errors(bindingResult));
            model.addAttribute("user", user);
            model.addAttribute("roles", Accessor.findList(Role.class, Filter.get().field("active", true)));
            model.addAttribute("permissions", Accessor.findList(Permission.class, Filter.get().field("active", true)));
            return "users/create";
        }

        userRepository.save(user);
        return "redirect:/users";
    }

    @RequestMapping(value = "/users/resetPasswordToDefault/{uuid}", method = RequestMethod.GET)
    public String resetPAsswordToDefault(@PathVariable String uuid) {
        User user = userService.getUserByUuid(uuid);
        user.setPassword(" ");
        userService.save(user);
        return "redirect:/users?msg=Password reset successful";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
    @RequestMapping(value = "/users/edit/{uuid}", method = RequestMethod.GET)
    public String showEditForm(Model model,
                               @PathVariable("uuid") String uuid
    ) {
        User user = Accessor.findOne(User.class, "uuid", uuid);
        if (null == user) {
            return "redirect:/users/create";
        }

        model.addAttribute("user", user);
        model.addAttribute("edit", "edit");
        model.addAttribute("roles", Accessor.findList(Role.class, Filter.get().field("active", true)));
        model.addAttribute("permissions", Accessor.findList(Permission.class, Filter.get().field("active", true)));
        return "users/create";
    }
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
    @RequestMapping(value = "/users/edit", method = RequestMethod.POST)
    public String submitEditForm(@ModelAttribute User acc, BindingResult bindingResult,
                                 Model model
    ) {
        /* System.out.println("Inside Submit Edit Form");
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", Utility.errors(bindingResult));
            model.addAttribute("user", acc);
            model.addAttribute("edit", "edit");
            model.addAttribute("roles", Accessor.findList(Role.class, Filter.get().field("active", true)));
            model.addAttribute("permissions", Accessor.findList(Permission.class, Filter.get().field("active", true)));

            System.out.println("Binding errors happened : " + Utility.errors(bindingResult));
            return "users/create";
        }*/

        User oldUser = Accessor.findOne(User.class, acc.getId());

        if (Objects.isNull(oldUser)) {
            userRepository.save(acc);
            System.out.println("User is null");
            return "redirect:/users";
        }

        User user = updateUser(acc, oldUser);

        //This should be the only place in the System where a username can be changed
        if (StringUtils.isNotBlank(acc.getUsername()) && acc.getUsername() != oldUser.getUsername()) {
            user.setUsername(acc.getUsername());
        }

        userRepository.save(user);
        return "redirect:/users";
    }

    public static User updateUser(User userForm, User oldUser) {
        if (StringUtils.isNotEmpty(userForm.getEmail())) {
            oldUser.setEmail(userForm.getEmail());
        }

        if (StringUtils.isNotEmpty(userForm.getGender())) {
            oldUser.setGender(userForm.getGender());
        }


        if (StringUtils.isNotEmpty(userForm.getFirstname())) {
            oldUser.setFirstname(userForm.getFirstname());
        }

        if (StringUtils.isNotEmpty(userForm.getPassword())) {
            oldUser.setPassword(userForm.getPassword());
        }

        if (StringUtils.isNotEmpty(userForm.getLastname())) {
            oldUser.setLastname(userForm.getLastname());
        }

        if (null != userForm.getCountry()) {
            oldUser.setCountry(userForm.getCountry());
        }

        if (null != userForm.getDistrict()) {
            oldUser.setDistrict(userForm.getDistrict());
        }

        if (null != userForm.getState()) {
            oldUser.setState(userForm.getState());
        }


        if (null != userForm.getPermissions()) {
            //oldUser.setPermissions(userForm.getPermissions());
        }

        if (null != userForm.getRoles()) {
            oldUser.setRoles(userForm.getRoles());
        }

        return oldUser;
    }


    //end of class
}

