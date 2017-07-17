package org.ng.undp.vdms.services.evaluations;

import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.evaluations.ConsultantEvaluation;
import org.ng.undp.vdms.repositories.evaluations.ConsultantEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by macbook on 6/30/17.
 */

@Service
public class ConsultantEvaluationService {
    @Autowired
    private ConsultantEvaluationRepository consultantEvaluationRepository;

    public ConsultantEvaluation save(ConsultantEvaluation consultantEvaluation){

        return  consultantEvaluationRepository.save(consultantEvaluation);
    }
    public ConsultantEvaluation findOneByContract(Contract consultantEvaluation){

        return  consultantEvaluationRepository.findOneByContract(consultantEvaluation);
    }


    public ConsultantEvaluation findOneByProjectNumber(String consultantEvaluation){

        return  consultantEvaluationRepository.findOneByProjectNumber(consultantEvaluation);
    }

    public List<ConsultantEvaluation> findAll(){


        return  consultantEvaluationRepository.findAll();
    }

}
