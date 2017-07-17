package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.Faq;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.repositories.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by macbook on 6/11/17.
 */
@Service
public class    FaqService {
    @Autowired
    private FaqRepository faqRepository;



    public Iterable<Faq> findAll() {

        return faqRepository.findAll();
    }



    public Faq getFaq(Long id) {
        return faqRepository.findOne(id);
    }

    public Faq editVpa(Faq vpa) {
        return faqRepository.save(vpa);
    }

    public Faq save(Faq d) {
        return faqRepository.save(d);
    }

    public void delete(Long id) {
        faqRepository.delete(id);
    }


    public List<Faq> findAllById(List<Long> FaqIds) {
        return faqRepository.findAllById(FaqIds);
    }
}
