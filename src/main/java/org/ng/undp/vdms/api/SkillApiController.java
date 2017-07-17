package org.ng.undp.vdms.api;

import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.dao.Param;
import org.ng.undp.vdms.domains.Skill;
import org.ng.undp.vdms.domains.Vss;
import org.ng.undp.vdms.services.SkillService;
import org.ng.undp.vdms.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by macbook on 4/4/17.
 */

@RestController
@RequestMapping(value = "api")
public class SkillApiController {

    @Autowired
    private SkillService skillService;


    @RequestMapping(method = RequestMethod.GET, value = "skills")

    public List<Skill> getVss(HttpServletRequest request,final Model model) {
        Param p = Utility.getParam(request);
        p.setSort("name");
      return Accessor.findList(Skill.class, Filter.get());



    }
}
