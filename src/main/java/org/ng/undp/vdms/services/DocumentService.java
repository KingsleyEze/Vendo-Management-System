package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.Document;
import org.ng.undp.vdms.domains.Faq;
import org.ng.undp.vdms.repositories.DocumentRepository;
import org.ng.undp.vdms.repositories.FaqRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by macbook on 6/16/17.
 */

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;



    public Iterable<Document> findAll() {

        return documentRepository.findAll();
    }
}
