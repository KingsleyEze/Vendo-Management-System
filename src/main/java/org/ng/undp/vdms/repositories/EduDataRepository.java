package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.consultants.EducationalData;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emmanuel on 6/27/17.
 */
public interface EduDataRepository extends CrudRepository<EducationalData, Long>, JpaSpecificationExecutor<EducationalData> {
}
