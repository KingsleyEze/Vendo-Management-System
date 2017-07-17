package org.ng.undp.vdms.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.SupplierExperience;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.SupplierContractDto;
import org.ng.undp.vdms.dto.SupplierExperienceDto;
import org.ng.undp.vdms.repositories.SupplierExperienceRepository;
import org.ng.undp.vdms.utils.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/18/2017.
 */

@Service
public class SupplierExperienceService {

    @Autowired
    private SupplierExperienceRepository repository;
    @Autowired
    private SupplierContractService contractService;

    public SupplierExperience save(SupplierExperience experience){
        return repository.save(experience);
    }

    public SupplierExperience findByUser(User user){
        return repository.findByUser(user);
    }

    public SupplierExperience createSupplierExperience(SupplierExperienceDto dto){

        ModelMapper mapper =  new ModelMapper();

        Gson gson =  new Gson();

        User user = Auth.INSTANCE.getAuth().get();

        SupplierExperience experience = mapper.map(dto,SupplierExperience.class);

        experience.setUser(user);

        this.save(experience);

        SupplierContractDto[] contractDtoArray =
                gson.fromJson(dto.getSupplierContractArray(), SupplierContractDto[].class);

        if(contractDtoArray != null){

            for(SupplierContractDto contractDto : contractDtoArray)
                contractService.createContract(contractDto, user, experience);
        }

        return experience;
    }

    public SupplierExperienceDto populateExperience(User user) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(
                    PropertyNamingStrategy.LOWER_CAMEL_CASE);
        ModelMapper modelMapper =  new ModelMapper();
        String contractArray = null;

        SupplierExperienceDto dto = null;
        SupplierExperience experience =  this.findByUser(user);

        if(!Objects.isNull(experience)){

            dto =  modelMapper.map(experience, SupplierExperienceDto.class);

            try {

                contractArray = objectMapper.writeValueAsString(
                        experience.getSupplierContracts().toArray());

                dto.setSupplierContractArray(contractArray);
            }catch (JsonProcessingException e){
                e.printStackTrace();

            }
        }


        return dto;
    }
}
