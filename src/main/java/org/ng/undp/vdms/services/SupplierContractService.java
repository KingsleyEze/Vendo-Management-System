package org.ng.undp.vdms.services;

import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.SupplierContract;
import org.ng.undp.vdms.domains.SupplierExperience;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.SupplierContractDto;
import org.ng.undp.vdms.repositories.SupplierContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class SupplierContractService {

    @Autowired
    private SupplierContractRepository repository;

    public SupplierContract save(SupplierContract contract){
        return repository.save(contract);
    }

    public SupplierContract createContract(SupplierContractDto contractDto, User user, SupplierExperience experience) {

        ModelMapper mapper =  new ModelMapper();
        SupplierContract contract =  mapper.map(contractDto, SupplierContract.class);

        if(contractDto.getId() != null)
            contract.setId(contractDto.getId());

            contract.setUser(user);
            contract.setSupplierExperience(experience);

        return this.save(contract);

    }
}
