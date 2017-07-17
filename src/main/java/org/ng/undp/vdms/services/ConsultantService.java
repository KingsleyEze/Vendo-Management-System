package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.Consultant;
import org.ng.undp.vdms.repositories.ConsultantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by abdulhakim on 11/12/16.
 */


@Service
public class ConsultantService {

    @Autowired
    private ConsultantRepository consultantRepository;
    public Consultant findOne(Long id){
        return consultantRepository.findOne(id);
    }

    public Consultant findByUserId(Long id){
        return consultantRepository.findByUserId(id);
    }
}
