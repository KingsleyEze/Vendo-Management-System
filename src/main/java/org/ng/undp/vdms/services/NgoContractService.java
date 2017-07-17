package org.ng.undp.vdms.services;

import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.NgoContract;
import org.ng.undp.vdms.domains.NgoExperience;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.NgoContractDto;
import org.ng.undp.vdms.repositories.NgoContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class NgoContractService {

    @Autowired
    private NgoContractRepository repository;

    public NgoContract save(NgoContract contract){
        return repository.save(contract);
    }

    public NgoContract createContract(NgoContractDto contractDto, User user, NgoExperience experience) {

        ModelMapper mapper =  new ModelMapper();
        NgoContract contract =  mapper.map(contractDto, NgoContract.class);

        if(contractDto.getId() != null)
            contract.setId(contractDto.getId());

            contract.setUser(user);
            contract.setNgoExperience(experience);

        return this.save(contract);

    }
}
