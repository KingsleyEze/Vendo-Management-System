package org.ng.undp.vdms.controllers.security;

import org.ng.undp.vdms.domains.security.MethodSecurity;
import org.ng.undp.vdms.security.Security;
import org.ng.undp.vdms.services.security.MethodSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by emmanuel on 12/2/16.
 */
@RequestMapping(value = "methodSecurities")
@RestController
@Security
public class MethodSecurityController {

    @Autowired
    MethodSecurityService methodSecurityService;

    @ResponseBody
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public Iterable<MethodSecurity> allMethodSecuritys() {
        return this.methodSecurityService.findAll();
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public MethodSecurity findMethodSecurityById(@PathVariable Long id, Model model, MethodSecurity methodSecurity, final BindingResult bindingResult) {
        return methodSecurityService.findById(id);
    }

    @RequestMapping(value = "findByMethod", method = RequestMethod.POST)
    public MethodSecurity findByMethod(@RequestBody String name) {
        return this.methodSecurityService.findByMethod(name);
    }

    @RequestMapping(value = "findByClazz", method = RequestMethod.POST)
    public MethodSecurity findByClazz(@RequestBody Long clazzId) {
        return this.methodSecurityService.findByClazz(clazzId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public MethodSecurity addMethodSecurity(@RequestBody MethodSecurity methodSecurity) {
        //List<MethodSecurity> e = methodSecurityService.findById(methodSecurity.getMethodSecurity().getId());
        //methodSecurity.setMethodSecurity(e);
        return methodSecurityService.save(methodSecurity);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public MethodSecurity updateMethodSecurity(@RequestBody MethodSecurity updatedMethodSecurity) {
        return methodSecurityService.save(updatedMethodSecurity);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public void deleteMethodSecurity(@PathVariable Long id) {
        methodSecurityService.deleteById(id);
    }

}

