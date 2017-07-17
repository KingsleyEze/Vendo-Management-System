package org.ng.undp.vdms.repositories.security;

import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.security.Role;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

/**
 * Created by emmanuel on 12/1/16.
 */
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findOneByName(String name);

    @Query("SELECT q FROM Role q WHERE  q.id IN (:vpaIds)")
    public Set<Role> findAllById(@Param("vpaIds") List<Long> vpaIds);
}
