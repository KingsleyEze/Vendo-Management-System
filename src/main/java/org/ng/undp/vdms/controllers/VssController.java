package org.ng.undp.vdms.controllers;

import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.dao.Param;
import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.Vss;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.services.VpaService;
import org.ng.undp.vdms.services.VssService;
import org.ng.undp.vdms.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by abdulhakim on 11/12/16.
 */

@Controller
@RequestMapping("vsses")
public class VssController extends BaseController {


    @Autowired
    private VpaService vpaService;
    @Autowired
    private VssService vssService;


    @RequestMapping(method = RequestMethod.GET)

    public String getVss(HttpServletRequest request,final Model model) {
        Param p = Utility.getParam(request);
        p.setSort("name");

        model.addAttribute("vsses", Accessor.findList(Vss.class, Filter.get(),p));

        return "vsses/index";

    }

    @RequestMapping(method = RequestMethod.GET, value = "add")

    public String addVpa(HttpServletRequest request,final Model model) {
        Param p = Utility.getParam(request);
        p.setSort("name");
        model.addAttribute("vpa", new Vss());
        model.addAttribute("vpas", Accessor.findList(Vpa.class, Filter.get(),p));

        return "vsses/add";

    }


    @RequestMapping(method = RequestMethod.GET, value = "edit/{id}")

    public String EditVpa(@PathVariable Long id, HttpServletRequest request, final Model model) {
        Param p = Utility.getParam(request);
        p.setSort("name");

        model.addAttribute("vpa", vssService.getVss(id));
        model.addAttribute("vpas", Accessor.findList(Vpa.class, Filter.get(),p));


        return "vsses/edit";

    }

    @RequestMapping(method = RequestMethod.POST, value = "edit/{id}")

    public String EditVpa(@PathVariable Long id, Vss vpa, BindingResult bindingResult, HttpServletRequest request, final Model model) {



        Long vpaId = Long.parseLong(request.getParameter("vpa_id"));
        System.out.println("THE VPA FOR VSS is " + vpaId);
        if(vpaId != null) {
            Vpa v = vpaService.getVpa(vpaId);
            vpa.setVpa(v);
            vssService.save(vpa);
        }
        return "redirect:/vsses";

    }

    @RequestMapping(method = RequestMethod.GET, value = "delete/{id}")

    public String DeleteVpa(@PathVariable Long id, final Model model) {

        model.addAttribute("vpa", vssService.getVss(id));

        return "vsses/delete";

    }

    @RequestMapping(method = RequestMethod.POST, value = "delete/{id}")

    public String postDeleteVss(@PathVariable Long id, final Model model) {


        vssService.delete(id);

        return "redirect:/vsses";

    }


    @RequestMapping(method = RequestMethod.POST, value = "add")

    public String addVpa(Vss vpa, final Model model) {


        Vss vpa1 = vpa;
        vssService.save(vpa1);


        return "redirect:/vsses";

    }


    @ResponseBody
    @RequestMapping(value = "{vpa_id}/{usertype}", method = RequestMethod.GET)
    public List<Vss> getVpaByUsertype(@PathVariable Long vpa_id, @PathVariable String usertype) {
        System.out.println("THe submitted usertype is : " + usertype);
        Vpa vpa = vpaService.getVpa(vpa_id);
        UserType userType = UserType.valueOf(usertype.toUpperCase());
        return vssService.findAllByUsertypeAndVpa(userType, vpa);
    }


    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Iterable<Vss> editVss() {

        return vssService.findAll();
    }

    @ResponseBody
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void deleteVss(@PathVariable Long id) {

        vssService.delete(id);
    }


}
