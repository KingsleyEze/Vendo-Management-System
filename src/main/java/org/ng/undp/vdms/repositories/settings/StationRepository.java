package org.ng.undp.vdms.repositories.settings;

import org.ng.undp.vdms.domains.settings.Department;
import org.ng.undp.vdms.domains.settings.Station;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by macbook on 6/26/17.
 */
public interface StationRepository  extends CrudRepository<Station, Long>, JpaSpecificationExecutor<Station> {

    public Iterable<Station> findAll();





    @Query("SELECT q FROM Station q WHERE  q.id IN (:FaqIds)")
    public List<Station> findAllById(@Param("FaqIds") List<Long> FaqIds);
}
