package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Document;
import org.ng.undp.vdms.domains.Faq;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by macbook on 6/16/17.
 */
public interface  DocumentRepository extends CrudRepository<Document, Long>, JpaSpecificationExecutor<Document> {

    public Iterable<Document> findAll();





}
