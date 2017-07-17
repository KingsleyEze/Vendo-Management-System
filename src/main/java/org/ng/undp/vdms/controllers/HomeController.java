package org.ng.undp.vdms.controllers;

import com.github.mkopylec.recaptcha.validation.RecaptchaValidator;
import com.github.mkopylec.recaptcha.validation.ValidationResult;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.dao.Pager;
import org.ng.undp.vdms.dao.Param;
import org.ng.undp.vdms.domains.Faq;
import org.ng.undp.vdms.domains.Notice;
import org.ng.undp.vdms.domains.User;

import org.ng.undp.vdms.domains.Vendor;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.services.SmtpService;
import org.ng.undp.vdms.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by abdulhakim on 10/12/16.
 */
@RequestMapping("/")
@Controller
public class HomeController extends BaseController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @Autowired
    private RecaptchaValidator recaptchaValidator;

    @Autowired
    private SmtpService smtpService;

    @RequestMapping(method = RequestMethod.GET)

    public String index(Principal principal, Model model) {


        //Get the number of vendors

        int vendorsNum = 10;
        Set<Role> roles = new HashSet<>();
        Role vendorRole = roleService.findByName("VENDOR");
        roles.add(vendorRole);


        vendorsNum = Accessor.findList(User.class, Filter.get().field("deleted_at").isNull().field("roles").contains(vendorRole.getId())).size();


        model.addAttribute("vendorsNum", vendorsNum);

        return "index";
    }

    @RequestMapping(method = RequestMethod.GET, value = "terms")

    public String terms(Model model) {


        return "terms";
    }


    @RequestMapping(method = RequestMethod.GET, value = "contact")

    public String contact(Model model) {


        return "contact";
    }

    @PostMapping("/contact")
    public void validateCaptcha(HttpServletRequest request, Model model) {
        ValidationResult result = recaptchaValidator.validate(request);
        String name, email, phone, message, successMessage, errorMessage;

        name = request.getParameter("name");
        email = request.getParameter("email");
        phone = request.getParameter("phone");
        message = request.getParameter("message");
        successMessage = "Thank you for contacting us, We will be in touch.";
        errorMessage = "Your message appears incorrect, Please fill every field!";


        if (result.isSuccess()) {
            logger.info("Successfully validated captcha");


            sendContactEmail(email, message, phone, name);
            model.addAttribute("success", successMessage);


        } else {
            model.addAttribute("error", errorMessage);
            model.addAttribute("email", email);
            model.addAttribute("name", name);
            model.addAttribute("phone", phone);
            model.addAttribute("message", message);


        }
    }

    @GetMapping(value = "/showCriteria")
    public String showCriteriaBeforeLandingPage(HttpServletRequest request) {

        return "showcriteria";
    }


    @RequestMapping(method = RequestMethod.GET, value = "/home/faqs")

    public String getFaqs(Principal principal, HttpServletRequest request, Model model) {

        //Get the number of vendors
        Param p = Utility.getParam(request);
        p.setSize(20);


        List<Faq> faqList = Accessor.findList(Faq.class, Filter.get(), p);


        model.addAttribute("vendorsNum", faqList);

        Long total = Accessor.count(Faq.class, Filter.get());

        model.addAttribute("pager", new Pager(total, p.getPage(), p.getSize()));


        model.addAttribute("count", total);

        model.addAttribute("pageSize", p.getSize());

        model.addAttribute("currentPage", p.getPage());

        model.addAttribute("from", p.getSize() * p.getPage() + 1);

        model.addAttribute("to", p.getSize() * (p.getPage() + 1));

        Pager pager = new Pager(total, p.getPage(), p.getSize(), p.getUrl());

        model.addAttribute("pager", pager);


        return "faq";
    }


    private void sendContactEmail(String email, String message, String phone, String name) {

        final Context messageContext = new Context();
        String redirectionUrl = ServletUriComponentsBuilder.fromCurrentContextPath().path("/").build().toUriString();

        messageContext.setVariable("homeUrl", redirectionUrl);
        messageContext.setVariable("name", name);
        messageContext.setVariable("email", email);
        messageContext.setVariable("phone", phone);
        messageContext.setVariable("message", message);

        String messageBody =
                smtpService.prepareThymeleafMailBody("contact-mail", messageContext);

        smtpService.sendSmtpAsync("hakeemhal@yahoo.com", "VDMS Web2Email Request ", messageBody, "", "");

    }
}
