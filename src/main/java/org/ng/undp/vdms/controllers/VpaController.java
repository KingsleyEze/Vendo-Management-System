package org.ng.undp.vdms.controllers;

import org.apache.commons.collections.CollectionUtils;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.services.VpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by abdulhakim on 11/12/16.
 */

@Controller
@RequestMapping(value = "vpas")
public class VpaController extends  BaseController {

    @Autowired
    private VpaService vpaService ;


    @RequestMapping(method = RequestMethod.GET)

    public String getVpas(final Model model){

          model.addAttribute("vpas", Accessor.findList(Vpa.class, Filter.get()));

          return "vpas/index";

    }

    @RequestMapping(method = RequestMethod.GET, value="add")

    public String addVpa(final Model model){

        model.addAttribute("vpa",new Vpa());

        return "vpas/add";

    }


    @RequestMapping(method = RequestMethod.GET, value="edit/{id}")

    public String EditVpa(@PathVariable Long id ,final Model model){

        model.addAttribute("vpa",vpaService.getVpa(id));

        return "vpas/edit";

    }
    @RequestMapping(method = RequestMethod.POST, value="edit/{id}")

    public String EditVpa(@PathVariable Long id, Vpa vpa ,final Model model){



       vpaService.save(vpa);

        return "redirect:/vpas";

    }
    @RequestMapping(method = RequestMethod.GET, value="delete/{id}")

    public String DeleteVpa(@PathVariable Long id ,final Model model){

        model.addAttribute("vpa",vpaService.getVpa(id));

        return "vpas/delete";

    }
    @RequestMapping(method = RequestMethod.POST, value="delete/{id}")

    public String postDeleteVpa(@PathVariable Long id ,final Model model){



        vpaService.delete(id);

        return "redirect:/vpas";

    }



    @RequestMapping(method = RequestMethod.POST, value="add")

    public String addVpa(Vpa vpa, final Model model){


        Vpa vpa1 = vpa;
        vpaService.save(vpa1);



        return "redirect:/vpas";

    }

    @ResponseBody
    @RequestMapping(value ="{usertype}", method = RequestMethod.GET)
    public List<Vpa> getVpaByUsertype(@PathVariable String usertype){
        System.out.println("THe submitted usertype is : " + usertype);
        UserType userType = UserType.valueOf(usertype.toUpperCase());
        //return  vpaService.findAllByUsertype(userType);

        return  Accessor.findList(Vpa.class, Filter.get().field("userType", usertype));

    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public Iterable<Vpa> postVpas(){

        //return  vpaService.findAll();
        return  Accessor.findList(Vpa.class, Filter.get());

    }

    @ResponseBody
    @RequestMapping(value = "{id}",method = RequestMethod.PUT)
    public Iterable<Vpa> editVpas(){

        //return  vpaService.findAll();
        return  Accessor.findList(Vpa.class, Filter.get());
    }



}
