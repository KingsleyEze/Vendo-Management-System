package org.ng.undp.vdms.repositories.evaluations;


import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.evaluations.NGOEvaluation;
import org.ng.undp.vdms.domains.evaluations.SupplierEvaluation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by macbook on 6/30/17.
 */
public interface NGOEvaluationRepository extends CrudRepository<NGOEvaluation, Long>, JpaSpecificationExecutor<NGOEvaluation> {

    public NGOEvaluation save(NGOEvaluation consultantEvaluation);

    public List<NGOEvaluation> findAll();

    public NGOEvaluation findOneByContract(Contract contract);

    public NGOEvaluation findOneByContractNumber(String contract);


}
