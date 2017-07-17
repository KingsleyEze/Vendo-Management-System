package org.ng.undp.vdms.controllers.security;

import org.ng.undp.vdms.domains.security.PathSecurity;
import org.ng.undp.vdms.security.Security;
import org.ng.undp.vdms.services.security.PathSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by emmanuel on 12/2/16.
 */
@RequestMapping(value = "pathSecurities")
@RestController
@Security
public class PathSecurityController {

    @Autowired
    PathSecurityService pathSecurityService;

    @ResponseBody
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public Iterable<PathSecurity> allPathSecurities() {
        return this.pathSecurityService.findAll();
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public PathSecurity findPathSecurityById(@PathVariable Long id, Model model, PathSecurity pathSecurity, final BindingResult bindingResult) {
        return pathSecurityService.findById(id);
    }

    @RequestMapping(value = "findByName", method = RequestMethod.POST)
    public PathSecurity findByName(@RequestBody String name) {
        return this.pathSecurityService.findByName(name);
    }

    @RequestMapping(value = "findByPermission", method = RequestMethod.POST)
    public PathSecurity findByPermission(@RequestBody Long permissionId) {
        return this.pathSecurityService.findByPermission(permissionId);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public PathSecurity addPathSecurity(@RequestBody PathSecurity pathSecurity) {
        //List<PathSecurity> e = pathSecurityService.findById(pathSecurity.getPathSecurity().getId());
        //pathSecurity.setPathSecurity(e);



        return pathSecurityService.save(pathSecurity);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public PathSecurity updatePathSecurity(@RequestBody PathSecurity updatedPathSecurity) {
        return pathSecurityService.save(updatedPathSecurity);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public void deletePathSecurity(@PathVariable Long id) {
        pathSecurityService.deleteById(id);
    }

}

