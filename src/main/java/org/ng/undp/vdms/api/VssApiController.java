package org.ng.undp.vdms.api;

import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.dao.Param;
import org.ng.undp.vdms.domains.Vss;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.services.VpaService;
import org.ng.undp.vdms.services.VssService;
import org.ng.undp.vdms.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by macbook on 4/4/17.
 */

@RestController
@RequestMapping(value = "api")
public class VssApiController {

    @Autowired
    private VpaService vpaService ;
    @Autowired
    private VssService vssService;


    @RequestMapping(method = RequestMethod.GET, value = "vsses/{vpaId}/{usertype}")

    public Iterable<Vss> getVss(@PathVariable String[] vpaId, @PathVariable UserType usertype ){
        List<Long> w = new ArrayList<Long>();
    for(String g : vpaId){

       w.add(Long.parseLong(g));
    }

        System.out.println("The IDs" + vpaId.length);
        return vssService.findAllByVpa(w, usertype);



    }


    @RequestMapping(method = RequestMethod.GET, value = "vsses/all")

    public List<Vss> getAllVss(HttpServletRequest request){
        Param p = Utility.getParam(request);
        p.setSort("name");

        return Accessor.findList(Vss.class, Filter.get(),p);



    }
}
