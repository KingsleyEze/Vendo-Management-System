package org.ng.undp.vdms.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.NgoExperience;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.NgoContractDto;
import org.ng.undp.vdms.dto.NgoExperienceDto;
import org.ng.undp.vdms.repositories.NgoExperienceRepository;
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
public class NgoExperienceService {

    @Autowired
    private NgoExperienceRepository repository;
    @Autowired
    private NgoContractService contractService;

    public NgoExperience save(NgoExperience experience){
        return repository.save(experience);
    }

    public NgoExperience findByUser(User user){
        return repository.findByUser(user);
    }

    public NgoExperience createNgoExperience(NgoExperienceDto dto){

        ModelMapper mapper =  new ModelMapper();

        Gson gson =  new Gson();

        User user = Auth.INSTANCE.getAuth().get();

        NgoExperience experience = mapper.map(dto,NgoExperience.class);

        experience.setUser(user);

        this.save(experience);

        NgoContractDto[] contractDtoArray =
                gson.fromJson(dto.getNgoContractArray(), NgoContractDto[].class);

        if(contractDtoArray != null){

            for(NgoContractDto contractDto : contractDtoArray)
                contractService.createContract(contractDto, user, experience);
        }

        return experience;
    }

    public NgoExperienceDto populateExperience(User user) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(
                    PropertyNamingStrategy.LOWER_CAMEL_CASE);
        ModelMapper modelMapper =  new ModelMapper();
        String contractArray = null;

        NgoExperienceDto dto = null;
        NgoExperience experience =  this.findByUser(user);

        if(!Objects.isNull(experience)){

            dto =  modelMapper.map(experience, NgoExperienceDto.class);

            try {

                contractArray = objectMapper.writeValueAsString(
                        experience.getNgoContracts().toArray());

                dto.setNgoContractArray(contractArray);
            }catch (JsonProcessingException e){
                e.printStackTrace();

            }
        }


        return dto;
    }
}
