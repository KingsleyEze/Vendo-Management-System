package org.ng.undp.vdms.controllers;

import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.dao.Param;
import org.ng.undp.vdms.domains.Skill;
import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.Vss;
import org.ng.undp.vdms.services.SkillService;
import org.ng.undp.vdms.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by macbook on 3/29/17.
 */


@Controller
@RequestMapping("skills")

public class SkillsController extends BaseController {

    @Autowired
    private SkillService skillService;


    @RequestMapping(method = RequestMethod.GET)

    public String getVss(HttpServletRequest request,final Model model) {
        Param p = Utility.getParam(request);
        p.setSort("name");

        model.addAttribute("skill", Accessor.findList(Skill.class, Filter.get(),p));

        return "skills/index";

    }

    @RequestMapping(method = RequestMethod.GET, value = "add")

    public String addVpa(final Model model) {

        model.addAttribute("vpa", new Skill());

        return "skills/add";

    }


    @RequestMapping(method = RequestMethod.GET, value = "edit/{id}")

    public String EditVpa(@PathVariable Long id, final Model model) {


        model.addAttribute("vpa", skillService.getSkill(id));
        return "skills/edit";

    }

    @RequestMapping(method = RequestMethod.POST, value = "edit/{id}")

    public String EditVpa(@PathVariable Long id, Skill vpa, HttpServletRequest request, final Model model) {


        skillService.save(vpa);

        return "redirect:/skills";

    }

    @RequestMapping(method = RequestMethod.GET, value = "delete/{id}")

    public String DeleteVpa(@PathVariable Long id, final Model model) {

        model.addAttribute("vpa", skillService.getSkill(id));
        return "skills/delete";

    }

    @RequestMapping(method = RequestMethod.POST, value = "delete/{id}")

    public String postDeleteVss(@PathVariable Long id, final Model model) {


        skillService.delete(id);

        return "redirect:/skills";

    }


    @RequestMapping(method = RequestMethod.POST, value = "add")

    public String addVpa(Skill vpa, final Model model) {


        Skill vpa1 = vpa;
        skillService.save(vpa1);


        return "redirect:/skills";

    }


}
