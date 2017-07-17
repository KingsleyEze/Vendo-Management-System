package org.ng.undp.vdms.repositories.evaluations;


import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.evaluations.SupplierEvaluation;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by macbook on 6/30/17.
 */
public interface SupplierEvaluationRepository extends CrudRepository<SupplierEvaluation, Long>, JpaSpecificationExecutor<SupplierEvaluation> {

    public SupplierEvaluation save(SupplierEvaluation consultantEvaluation);

    public List<SupplierEvaluation> findAll();

    public SupplierEvaluation findOneByContract(Contract contract);

    public SupplierEvaluation findOneByContractNumber(String contract);


}
