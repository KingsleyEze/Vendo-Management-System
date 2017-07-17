package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.SupplierGood;
import org.ng.undp.vdms.domains.SupplierTechnical;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.SupplierGoodDto;
import org.ng.undp.vdms.repositories.SupplierGoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class SupplierGoodService {

    @Autowired
    private SupplierGoodRepository repository;

    public SupplierGood save(SupplierGood good){
        return  repository.save(good);
    }

    public SupplierGood createSupplierGood(SupplierGoodDto dto, SupplierTechnical technical, User user){

        SupplierGood supplierGood = new SupplierGood();

        if(dto.getId() != null)
            supplierGood.setId(dto.getId());

            supplierGood.setCode(dto.getCode());
            supplierGood.setQuality(dto.getQuality());
            supplierGood.setDescription(dto.getDescription());
            supplierGood.setSupplierTechnical(technical);
            supplierGood.setUser(user);

        return this.save(supplierGood);
    }
}
