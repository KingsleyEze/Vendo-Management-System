package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Notice;
import org.ng.undp.vdms.domains.Tender;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by macbook on 6/16/17.
 */
public interface TenderRepository extends CrudRepository<Tender, Long>, JpaSpecificationExecutor<Tender> {

    public Iterable<Tender> findAll();





}
