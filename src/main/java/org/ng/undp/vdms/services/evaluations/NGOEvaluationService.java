package org.ng.undp.vdms.services.evaluations;

import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.evaluations.NGOEvaluation;
import org.ng.undp.vdms.domains.evaluations.SupplierEvaluation;
import org.ng.undp.vdms.repositories.evaluations.NGOEvaluationRepository;
import org.ng.undp.vdms.repositories.evaluations.SupplierEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * Created by macbook on 6/30/17.
 */

@Service
public class NGOEvaluationService {
    @Autowired
    private NGOEvaluationRepository consultantEvaluationRepository;

    public NGOEvaluation save(NGOEvaluation consultantEvaluation){

        return  consultantEvaluationRepository.save(consultantEvaluation);
    }
    public NGOEvaluation findOneByContract(Contract consultantEvaluation){

        return  consultantEvaluationRepository.findOneByContract(consultantEvaluation);
    }


    public NGOEvaluation findOneByContractNumber(String consultantEvaluation){

        return  consultantEvaluationRepository.findOneByContractNumber(consultantEvaluation);
    }

    public List<NGOEvaluation> findAll(){


        return  consultantEvaluationRepository.findAll();
    }

}
