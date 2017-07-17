package org.ng.undp.vdms.repositories.security;

import org.ng.undp.vdms.domains.security.ClassSecurity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emmanuel on 12/1/16.
 */
public interface ClassSecurityRepository extends CrudRepository<ClassSecurity, Long> {
    public ClassSecurity findOneByName(String name);

}
