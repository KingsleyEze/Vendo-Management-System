package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.NgoDispute;
import org.ng.undp.vdms.repositories.NgoDisputeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class NgoDisputeService {

    @Autowired
    private NgoDisputeRepository repository;

    public NgoDispute save(NgoDispute dispute){
        return repository.save(dispute);
    }
}
