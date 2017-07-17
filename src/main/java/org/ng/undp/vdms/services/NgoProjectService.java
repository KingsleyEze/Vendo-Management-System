package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.NgoProject;
import org.ng.undp.vdms.repositories.NgoProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class NgoProjectService {

    @Autowired
    private NgoProjectRepository repository;

    public NgoProject save(NgoProject project){
        return repository.save(project);
    }
}
