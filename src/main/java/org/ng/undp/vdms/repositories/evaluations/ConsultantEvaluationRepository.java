package org.ng.undp.vdms.repositories.evaluations;


import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.evaluations.ConsultantEvaluation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by macbook on 6/30/17.
 */
public interface ConsultantEvaluationRepository extends CrudRepository<ConsultantEvaluation, Long>, JpaSpecificationExecutor<ConsultantEvaluation> {

    public ConsultantEvaluation save(ConsultantEvaluation consultantEvaluation);

    public List<ConsultantEvaluation> findAll();

    public ConsultantEvaluation findOneByContract(Contract contract);

    public ConsultantEvaluation findOneByProjectNumber(String contract);


}
