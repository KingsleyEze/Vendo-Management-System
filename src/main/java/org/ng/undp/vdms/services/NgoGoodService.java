package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.NgoGood;
import org.ng.undp.vdms.domains.NgoTechnical;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.NgoGoodDto;
import org.ng.undp.vdms.repositories.NgoGoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class NgoGoodService {

    @Autowired
    private NgoGoodRepository repository;

    public NgoGood save(NgoGood good){
        return  repository.save(good);
    }

    public NgoGood createNgoGood(NgoGoodDto dto, NgoTechnical technical, User user){

        NgoGood supplierGood = new NgoGood();

        if(dto.getId() != null)
            supplierGood.setId(dto.getId());

            supplierGood.setCode(dto.getCode());
            supplierGood.setQuality(dto.getQuality());
            supplierGood.setDescription(dto.getDescription());
            supplierGood.setNgoTechnical(technical);
            supplierGood.setUser(user);

        return this.save(supplierGood);
    }
}
