package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.NgoDocument;
import org.ng.undp.vdms.repositories.NgoDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class NgoDocumentService {

    @Autowired
    private NgoDocumentRepository repository;

    public NgoDocument save(NgoDocument document){
        return repository.save(document);
    }
}
