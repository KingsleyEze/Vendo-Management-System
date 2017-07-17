package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Faq;
import org.ng.undp.vdms.domains.Ngo;
import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.Faq;
import org.ng.undp.vdms.domains.constants.UserType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by macbook on 6/11/17.
 */
public interface FaqRepository  extends CrudRepository<Faq, Long>, JpaSpecificationExecutor<Faq> {

    public Iterable<Faq> findAll();





    @Query("SELECT q FROM Faq q WHERE  q.id IN (:FaqIds)")
    public List<Faq> findAllById(@Param("FaqIds") List<Long> FaqIds);



}
