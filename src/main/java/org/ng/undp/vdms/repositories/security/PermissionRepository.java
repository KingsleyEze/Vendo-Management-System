package org.ng.undp.vdms.repositories.security;

import org.ng.undp.vdms.domains.security.Permission;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emmanuel on 12/1/16.
 */
public interface PermissionRepository extends CrudRepository<Permission, Long> {

    public Permission findOneByName(String name);
    
}
