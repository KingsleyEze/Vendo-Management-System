package org.ng.undp.vdms.repositories.evaluations;

import org.ng.undp.vdms.domains.evaluations.ConsultantEvaluation;
import org.ng.undp.vdms.domains.evaluations.ConsultantLanguage;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by macbook on 6/30/17.
 */
public interface ConsultantLanguageRepository extends CrudRepository<ConsultantLanguage, Long>, JpaSpecificationExecutor<ConsultantLanguage> {

    public ConsultantLanguage save(ConsultantEvaluation consultantEvaluation);
    public List<ConsultantLanguage> findAll();



}
