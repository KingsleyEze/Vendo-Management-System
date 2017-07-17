package org.ng.undp.vdms.services.security;

import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.repositories.security.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by emmanuel on 12/2/16.
 */
@Service("roleService")
public class RoleService {

    @Autowired
    private RoleRepository repo;

    public Iterable<Role> findAll() {
        return repo.findAll();
    }

    public Role findById(Long id) {
        return repo.findOne(id);
    }

    public Role save(Role role) {
        if (!Objects.isNull(role)) {
            role = repo.save(role);
        }
        return role;
    }

    public void deleteById(Long id) {
        Role role = repo.findOne(id);
        if(!Objects.isNull(role)) {
            role.setActive(false);
            repo.save(role);
        }
    }

    public void delete(Role role){
        if(!Objects.isNull(role)){
            role.setActive(false);
            repo.save(role);
        }
    }

    public Set<Role> findAllById(List<Long> vpaIds){return repo.findAllById(vpaIds);}

    public Role findByName(String name) {
        return  repo.findOneByName(name);
    }

    /**
     * Used to generate a default role for a user based on the User Role type
     * @param roleName
     * @return
     */
    public  Set<Role> getDefaultRole(String roleName){
        Role role  = this.findByName(roleName);
        if(role == null){
            //return null if the roleName does not match any role in db,
            //because roles field in user should always be null if no default is provided
            return null;
        }

        Set<Role> roles = new HashSet<>(1);
        roles.add(role);
        return roles;
    }

}
