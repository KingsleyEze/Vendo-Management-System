package org.ng.undp.vdms.controllers.settings;

import org.ng.undp.vdms.controllers.BaseController;
import org.ng.undp.vdms.domains.Faq;
import org.ng.undp.vdms.domains.settings.Department;
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

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

/**
 * Created by macbook on 6/26/17.
 */

@Controller
@RequestMapping("departments")
public class DepartmentController extends BaseController {

    @Autowired
    DepartmentService faqService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(method = RequestMethod.GET)

    public String getFaqs(final Model model) {

        model.addAttribute("faqs", faqService.findAll());


        return "departments/index";

    }

    @RequestMapping(method = RequestMethod.GET, value = "add")

    public String addFaq(final Model model) {

        model.addAttribute("faq", new Department());

        return "departments/add";

    }


    @RequestMapping(method = RequestMethod.POST, value = "add")

    public String addFaq(Department faq, final Model model) {


        faqService.save(faq);
        success.add("Successfully added department");
        model.addAttribute("success", Utility.success(success));

        return "redirect:/departments";

    }

    @RequestMapping(method = RequestMethod.GET, value = "edit/{id}")

    public String EditFaq(@PathVariable Long id, final Model model) {

        model.addAttribute("faq", faqService.getDepartment(id));


        return "departments/edit";

    }

    @RequestMapping(method = RequestMethod.POST, value = "edit/{id}")

    public String EditFaq(@PathVariable Long id, Department faq, HttpServletRequest request, final Model model) {

        Department faq1 = faqService.getDepartment(id);
        faq1 = faq;

        faqService.save(faq1);
        success.add("Successfully updated department");
        model.addAttribute("success", Utility.success(success));


        return "redirect:/departments";

    }

    @RequestMapping(method = RequestMethod.GET, value = "delete/{id}")

    public String DeleteFaq(@PathVariable Long id, final Model model) {

        model.addAttribute("faq", faqService.getDepartment(id));

        return "departments/delete";

    }

    @RequestMapping(method = RequestMethod.POST, value = "delete/{id}")

    public String postDeleteVss(@PathVariable Long id, final Model model) {


        faqService.delete(id);
        success.add("Successfully deleted department");
        model.addAttribute("success", Utility.success(success));


        return "redirect:/departments";

    }

}
