package org.ng.undp.vdms.controllers.security;

import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.security.Permission;
import org.ng.undp.vdms.security.Security;
import org.ng.undp.vdms.services.security.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by emmanuel on 12/2/16.
 */
@RequestMapping(value = "/permissions")
@RestController
@Security
public class PermissionController {

    @Autowired
    PermissionService permissionService;

    @ResponseBody
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Iterable<Permission> allPermissions() {
        //return this.permissionService.findAll();
        return Accessor.findList(Permission.class, Filter.get().field("active", true));
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public Permission findPermissionById(@PathVariable Long id, Model model, Permission permission, final BindingResult bindingResult) {
        return permissionService.findById(id);
    }

    @RequestMapping(value = "/findByName", method = RequestMethod.POST)
    public Permission findByName(@RequestBody String name) {
        return this.permissionService.findByName(name);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Permission addPermission(@RequestBody Permission permission) {
        //List<Permission> e = permissionService.findById(permission.getPermission().getId());
        //permission.setPermission(e);
        return permissionService.save(permission);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Permission updatePermission(@RequestBody Permission updatedPermission) {
        return permissionService.save(updatedPermission);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public void deletePermission(@PathVariable Long id) {
        permissionService.deleteById(id);
    }

}

