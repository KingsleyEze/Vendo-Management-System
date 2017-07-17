package org.ng.undp.vdms.controllers.settings;

import org.ng.undp.vdms.controllers.BaseController;
import org.ng.undp.vdms.domains.settings.Agency;
import org.ng.undp.vdms.domains.settings.Department;
import org.ng.undp.vdms.services.settings.AgencyService;
import org.ng.undp.vdms.services.settings.DepartmentService;
import org.ng.undp.vdms.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by macbook on 6/26/17.
 */
@Controller
@RequestMapping("agencies")
public class AgencyController extends BaseController {


    @Autowired
    AgencyService faqService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping(method = RequestMethod.GET)

    public String getFaqs( Model model) {

        model.addAttribute("faqs", faqService.findAll());


        return "agencies/index";

    }

    @RequestMapping(method = RequestMethod.GET, value = "add")

    public String addFaq( Model model) {

        model.addAttribute("faq", new Agency());

        return "agencies/add";

    }


    @RequestMapping(method = RequestMethod.POST, value = "add")

    public String addFaq(Agency faq,  RedirectAttributes model) {


       if(null != faqService.save(faq)) {
           this.success.add("Successfully added agency");

           model.addFlashAttribute("success", Utility.success(success));
       }


        return "redirect:/agencies";

    }

    @RequestMapping(method = RequestMethod.GET, value = "edit/{id}")

    public String EditFaq(@PathVariable Long id, final Model model) {

        model.addAttribute("faq", faqService.getDepartment(id));


        return "agencies/edit";

    }

    @RequestMapping(method = RequestMethod.POST, value = "edit/{id}")

    public String EditFaq(@PathVariable Long id, Agency faq, HttpServletRequest request,  RedirectAttributes model) {

        Agency faq1 = faqService.getDepartment(id);
        faq1 = faq;

        if(null !=  faqService.save(faq1)) {
            success.add("Successfully updated agency");
            model.addFlashAttribute("success", Utility.success(success));

        }
        return "redirect:/agencies";

    }

    @RequestMapping(method = RequestMethod.GET, value = "delete/{id}")

    public String DeleteFaq(@PathVariable Long id, final Model model) {

        model.addAttribute("faq", faqService.getDepartment(id));

        return "agencies/delete";

    }

    @RequestMapping(method = RequestMethod.POST, value = "delete/{id}")

    public String postDeleteVss(@PathVariable Long id,  RedirectAttributes model) {
        if(null != id) {

            faqService.delete(id);
            success.add("Successfully deleted agency");
            model.addFlashAttribute("success", Utility.success(success));

        }


        return "redirect:/agencies";

    }


}

