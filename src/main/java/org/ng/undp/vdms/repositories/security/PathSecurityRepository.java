package org.ng.undp.vdms.repositories.security;

import org.ng.undp.vdms.domains.security.PathSecurity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emmanuel on 12/1/16.
 */
public interface PathSecurityRepository extends CrudRepository<PathSecurity, Long> {
    public PathSecurity findOneByName(String name);

    public PathSecurity findOneByPermission(Long permissionId);

}
