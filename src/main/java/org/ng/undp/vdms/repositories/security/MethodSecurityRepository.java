package org.ng.undp.vdms.repositories.security;

import org.ng.undp.vdms.domains.security.MethodSecurity;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emmanuel on 12/1/16.
 */
public interface MethodSecurityRepository extends CrudRepository<MethodSecurity, Long> {
    public MethodSecurity findOneByClazz(Long clazz);

    public MethodSecurity findOneByMethod(String method);
}
