package org.ng.undp.vdms.repositories.settings;

import org.ng.undp.vdms.domains.settings.Agency;
import org.ng.undp.vdms.domains.settings.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by macbook on 6/26/17.
 */
public interface AgencyRepository extends CrudRepository<Agency, Long>, JpaSpecificationExecutor<Agency> {

    public Iterable<Agency> findAll();





    @Query("SELECT q FROM Agency q WHERE  q.id IN (:FaqIds)")
    public List<Agency> findAllById(@Param("FaqIds") List<Long> FaqIds);
}
