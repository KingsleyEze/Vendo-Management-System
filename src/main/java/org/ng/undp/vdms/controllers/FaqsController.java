package org.ng.undp.vdms.controllers;

import org.ng.undp.vdms.domains.Faq;
import org.ng.undp.vdms.domains.Skill;
import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.Vss;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.services.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by abdulhakim on 11/12/16.
 */

@Controller
@RequestMapping("faqs")
public class FaqsController extends BaseController {


    @Autowired
    private FaqService faqService;


    @RequestMapping(method = RequestMethod.GET)

    public String getFaqs(final Model model) {

        model.addAttribute("faqs", faqService.findAll());


        return "faqs/index";

    }

    @RequestMapping(method = RequestMethod.GET, value = "add")

    public String addFaq(final Model model) {

        model.addAttribute("faq", new Faq());

        return "faqs/add";

    }


    @RequestMapping(method = RequestMethod.POST, value = "add")

    public String addFaq(Faq faq, final Model model) {


        faqService.save(faq);


        return "redirect:/faqs";

    }

    @RequestMapping(method = RequestMethod.GET, value = "edit/{id}")

    public String EditFaq(@PathVariable Long id, final Model model) {

        model.addAttribute("faq", faqService.getFaq(id));


        return "faqs/edit";

    }

    @RequestMapping(method = RequestMethod.POST, value = "edit/{id}")

    public String EditFaq(@PathVariable Long id, Faq faq, HttpServletRequest request, final Model model) {

        Faq faq1 = faqService.getFaq(id);
        faq1 = faq;

        faqService.save(faq1);

        return "redirect:/faqs";

    }

    @RequestMapping(method = RequestMethod.GET, value = "delete/{id}")

    public String DeleteFaq(@PathVariable Long id, final Model model) {

        model.addAttribute("faq", faqService.getFaq(id));

        return "faqs/delete";

    }

    @RequestMapping(method = RequestMethod.POST, value = "delete/{id}")

    public String postDeleteVss(@PathVariable Long id, final Model model) {


        faqService.delete(id);

        return "redirect:/faqs";

    }


}
