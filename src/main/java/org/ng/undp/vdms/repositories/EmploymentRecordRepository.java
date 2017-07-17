package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.consultants.EmploymentData;
import org.ng.undp.vdms.domains.consultants.EmploymentRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emmanuel on 6/27/17.
 */
public interface EmploymentRecordRepository extends CrudRepository<EmploymentRecord, Long>, JpaSpecificationExecutor<EmploymentRecord> {
}
