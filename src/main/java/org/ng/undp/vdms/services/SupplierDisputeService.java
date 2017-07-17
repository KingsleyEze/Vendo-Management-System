package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.SupplierDispute;
import org.ng.undp.vdms.repositories.SupplierDisputeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class SupplierDisputeService {

    @Autowired
    private SupplierDisputeRepository repository;

    public SupplierDispute save(SupplierDispute dispute){
        return repository.save(dispute);
    }
}
