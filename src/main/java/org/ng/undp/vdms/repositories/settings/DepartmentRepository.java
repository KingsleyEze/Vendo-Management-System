package org.ng.undp.vdms.repositories.settings;

import org.ng.undp.vdms.domains.Consultant;
import org.ng.undp.vdms.domains.Faq;
import org.ng.undp.vdms.domains.settings.Department;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by macbook on 6/26/17.
 */
public interface DepartmentRepository extends CrudRepository<Department, Long>, JpaSpecificationExecutor<Department> {

    public Iterable<Department> findAll();





    @Query("SELECT q FROM Department q WHERE  q.id IN (:FaqIds)")
    public List<Department> findAllById(@Param("FaqIds") List<Long> FaqIds);

}
