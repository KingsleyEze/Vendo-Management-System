package org.ng.undp.vdms.controllers.security;

import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.security.Security;
import org.ng.undp.vdms.services.security.PermissionService;
import org.ng.undp.vdms.services.security.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Created by emmanuel on 12/2/16.
 */
@RequestMapping(value = "roles")
@RestController
@Security
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    PermissionService permissionService;

    @ResponseBody
    @RequestMapping(value = "all", method = RequestMethod.GET)
    public Iterable<Role> allRoles() {

        //return this.roleService.findAll();
        return Accessor.findList(Role.class, Filter.get().field("active", true));
    }

    @RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
    public Role findRoleById(@PathVariable Long id, Model model, Role role, final BindingResult bindingResult) {
        return this.roleService.findById(id);
    }

    @RequestMapping(value = "findByName", method = RequestMethod.POST)
    public Role findByName(@RequestBody String name) {
        return this.roleService.findByName(name);
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Role addRole(@RequestBody Role role) {
        //List<Permission> e = permissionService.findById(role.getPermission().getId());
        //role.setPermission(e);
        return roleService.save(role);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public Role updateRole(@RequestBody Role updatedRole) {
        return roleService.save(updatedRole);
    }

    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    public void deleteRole(@PathVariable Long id) {
        this.roleService.deleteById(id);
    }

}

