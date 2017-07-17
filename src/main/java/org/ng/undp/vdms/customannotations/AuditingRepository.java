package org.ng.undp.vdms.customannotations;

import org.ng.undp.vdms.customannotations.AuditingEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by abdulhakim on 11/19/16.
 */
public interface AuditingRepository extends CrudRepository<AuditingEntity, Long>, JpaSpecificationExecutor<AuditingEntity> {
}
