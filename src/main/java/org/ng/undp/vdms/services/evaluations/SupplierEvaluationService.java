package org.ng.undp.vdms.services.evaluations;

import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.evaluations.SupplierEvaluation;
import org.ng.undp.vdms.repositories.evaluations.SupplierEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by macbook on 6/30/17.
 */

@Service
public class SupplierEvaluationService {
    @Autowired
    private SupplierEvaluationRepository consultantEvaluationRepository;

    public SupplierEvaluation save(SupplierEvaluation consultantEvaluation){

        return  consultantEvaluationRepository.save(consultantEvaluation);
    }
    public SupplierEvaluation findOneByContract(Contract consultantEvaluation){

        return  consultantEvaluationRepository.findOneByContract(consultantEvaluation);
    }


    public SupplierEvaluation findOneByContractNumber(String consultantEvaluation){

        return  consultantEvaluationRepository.findOneByContractNumber(consultantEvaluation);
    }

    public List<SupplierEvaluation> findAll(){


        return  consultantEvaluationRepository.findAll();
    }

}
