package org.ng.undp.vdms.auditlog;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by abdulhakim on 11/19/16.
 */
public interface LoginEntityRepository extends PagingAndSortingRepository<LoginEntity, Long>, JpaSpecificationExecutor<LoginEntity>{


public LoginEntity findByUsername(String username);
}
