package org.ng.undp.vdms.controllers.security;

import org.ng.undp.vdms.domains.security.ClassSecurity;
import org.ng.undp.vdms.security.Security;
import org.ng.undp.vdms.services.security.ClassSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by emmanuel on 12/2/16.
 */
@RequestMapping(value = "classSecurities")
@RestController
@Security
public class ClassSecurityController {

    @Autowired
    ClassSecurityService classSecurityService;

    @ResponseBody
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public Iterable<ClassSecurity> allClassSecurities() {
        return this.classSecurityService.findAll();
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public ClassSecurity findClassSecurityById(@PathVariable Long id, Model model, ClassSecurity classSecurity, final BindingResult bindingResult) {
        return classSecurityService.findById(id);
    }

    @RequestMapping(value = "findByName", method = RequestMethod.POST)
    public ClassSecurity findByName(@RequestBody String name) {
        return this.classSecurityService.findByName(name);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ClassSecurity addClassSecurity(@RequestBody ClassSecurity classSecurity) {
        //List<ClassSecurity> e = classSecurityService.findById(classSecurity.getClassSecurity().getId());
        //classSecurity.setClassSecurity(e);
        return classSecurityService.save(classSecurity);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public ClassSecurity updateClassSecurity(@RequestBody ClassSecurity updatedClassSecurity) {
        return classSecurityService.save(updatedClassSecurity);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public void deleteClassSecurity(@PathVariable Long id) {
        classSecurityService.deleteById(id);
    }

}

