package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Consultant;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by abdulhakim on 11/12/16.
 */
public interface ConsultantRepository  extends CrudRepository<Consultant, Long>, JpaSpecificationExecutor<Consultant> {

    public  Consultant findOne(Long id);

    public Consultant findByUserId(Long id);
    }
