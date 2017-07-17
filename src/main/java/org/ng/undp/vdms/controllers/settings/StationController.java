package org.ng.undp.vdms.controllers.settings;

import org.ng.undp.vdms.controllers.BaseController;
import org.ng.undp.vdms.domains.settings.Department;
import org.ng.undp.vdms.domains.settings.Station;
import org.ng.undp.vdms.services.settings.StationService;
import org.ng.undp.vdms.services.settings.StationService;
import org.ng.undp.vdms.utils.Utility;
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
@RequestMapping("stations")
public class StationController  extends BaseController{


    @Autowired
    StationService faqService;
    ArrayList<String> errors = new ArrayList<String>();
    ArrayList<String> success = new ArrayList<String>();
    ArrayList<String> stickyNotice = new ArrayList<String>();


    @RequestMapping(method = RequestMethod.GET)

    public String getFaqs(final Model model) {

        model.addAttribute("faqs", faqService.findAll());


        return "stations/index";

    }

    @RequestMapping(method = RequestMethod.GET, value = "add")

    public String addFaq(final Model model) {

        model.addAttribute("faq", new Station());

        return "stations/add";

    }


    @RequestMapping(method = RequestMethod.POST, value = "add")

    public String addFaq(Station faq, final RedirectAttributes model) {


        if(null != faqService.save(faq)) {
            success.add("Successfully added department");
            model.addFlashAttribute("success", Utility.success(success));
        }
        return "redirect:/stations";

    }

    @RequestMapping(method = RequestMethod.GET, value = "edit/{id}")

    public String EditFaq(@PathVariable Long id, final Model model) {

        model.addAttribute("faq", faqService.getStation(id));


        return "stations/edit";

    }

    @RequestMapping(method = RequestMethod.POST, value = "edit/{id}")

    public String EditFaq(@PathVariable Long id, Station faq, HttpServletRequest request, final RedirectAttributes model) {

        Station faq1 = faqService.getStation(id);
        faq1 = faq;
        if(null != faq1) {
            faqService.save(faq1);
            success.add("Successfully updated department");
            model.addFlashAttribute("success", Utility.success(success));
        }

        return "redirect:/stations";

    }

    @RequestMapping(method = RequestMethod.GET, value = "delete/{id}")

    public String DeleteFaq(@PathVariable Long id, final Model model) {

        model.addAttribute("faq", faqService.getStation(id));

        return "stations/delete";

    }

    @RequestMapping(method = RequestMethod.POST, value = "delete/{id}")

    public String postDeleteVss(@PathVariable Long id, final RedirectAttributes model) {

        if(null !=id) {
            faqService.delete(id);
            success.add("Successfully deleted department");
            model.addFlashAttribute("success", Utility.success(success));
        }

        return "redirect:/stations";

    }

}
