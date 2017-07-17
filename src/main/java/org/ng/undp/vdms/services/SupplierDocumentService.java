package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.SupplierDocument;
import org.ng.undp.vdms.repositories.SupplierDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class SupplierDocumentService {

    @Autowired
    private SupplierDocumentRepository repository;

    public SupplierDocument save(SupplierDocument document){
        return repository.save(document);
    }
}
