package org.ng.undp.vdms.services.evaluations;

import org.ng.undp.vdms.domains.evaluations.ConsultantEvaluation;
import org.ng.undp.vdms.domains.evaluations.ConsultantLanguage;
import org.ng.undp.vdms.repositories.evaluations.ConsultantEvaluationRepository;
import org.ng.undp.vdms.repositories.evaluations.ConsultantLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by macbook on 6/30/17.
 */

@Service
public class ConsultantLanguageService {
    @Autowired
    private ConsultantLanguageRepository consultantEvaluationRepository;

    public ConsultantLanguage save(ConsultantLanguage consultantEvaluation){

        return  consultantEvaluationRepository.save(consultantEvaluation);
    }

    public List<ConsultantLanguage> findAll(){

        return  consultantEvaluationRepository.findAll();
    }

}
