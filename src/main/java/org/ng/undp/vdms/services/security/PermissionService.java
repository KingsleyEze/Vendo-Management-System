package org.ng.undp.vdms.services.security;

import org.ng.undp.vdms.domains.security.Permission;
import org.ng.undp.vdms.repositories.security.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by emmanuel on 12/2/16.
 */
@Service("permissionService")
public class PermissionService {

    @Autowired
    private PermissionRepository repo;

    public Iterable<Permission> findAll() {
        return repo.findAll();
    }

    public Permission findById(Long id) {
        return repo.findOne(id);
    }

    public Permission save(Permission permission) {
        if (!Objects.isNull(permission)) {
            permission = repo.save(permission);
        }
        return permission;
    }

    public void deleteById(Long id) {
        Permission permission = repo.findOne(id);
        if(!Objects.isNull(permission)) {
            permission.setActive(false);
            repo.save(permission);
        }
    }

    public void delete(Permission permission){
        if(!Objects.isNull(permission)){
            permission.setActive(false);
            repo.save(permission);
        }
    }

    public Permission findByName(String name) {
        return repo.findOneByName(name);
    }

}
