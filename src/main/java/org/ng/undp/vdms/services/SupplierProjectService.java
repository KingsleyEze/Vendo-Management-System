package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.SupplierProject;
import org.ng.undp.vdms.repositories.SupplierProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class SupplierProjectService {

    @Autowired
    private SupplierProjectRepository repository;

    public SupplierProject save(SupplierProject project){
        return repository.save(project);
    }
}
