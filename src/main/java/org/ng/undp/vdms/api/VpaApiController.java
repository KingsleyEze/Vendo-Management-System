package org.ng.undp.vdms.api;

import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.dao.Param;
import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.Vss;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.services.VpaService;
import org.ng.undp.vdms.services.security.RoleService;
import org.ng.undp.vdms.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by macbook on 4/4/17.
 */

@RestController
@RequestMapping(value = "api")
public class VpaApiController {


    @Autowired
    private VpaService vpaService;

    @Autowired
    private RoleService roleService;


    @RequestMapping(method = RequestMethod.GET, value = "vpas")

    public List<Vpa> getVpas(HttpServletRequest request) {

        Param p = Utility.getParam(request);
        p.setSort("name");

        return Accessor.findList(Vpa.class, Filter.get(), p);


    }


    @RequestMapping(method = RequestMethod.GET, value = "vpas/{usertype}", produces = "application/json")

    public List<Vpa> getVpas(@PathVariable String usertype, HttpServletRequest request) {
        Param p = Utility.getParam(request);
        p.setSort("name");
        List<Vpa> vpas = Accessor.findList(Vpa.class, Filter.get().field("usertype", UserType.valueOf(usertype)), p);
        return vpas;

    }

    @RequestMapping(method = RequestMethod.GET, value = "vpasByRole/{roleID}", produces = "application/json")

    public List<Vpa> getVpasByList(@PathVariable String[] roleID, HttpServletRequest request) {
          ArrayList<UserType> userTypes = new ArrayList<UserType>(1);
          List<Long> roleIDS = new ArrayList<Long>(1);

          for(String r:roleID){
              System.out.println("The id is " + r);
              roleIDS.add(Long.parseLong(r));
          }

        Set<Role> roles =   roleService.findAllById(roleIDS);;
          for(Role s: roles){

            userTypes.add(UserType.valueOf(s.getName().toUpperCase()));
        }


        List<Vpa> vpas = vpaService.findAllByUsertype(userTypes);
        return vpas;

    }


}
