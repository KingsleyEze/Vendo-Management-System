package org.ng.undp.vdms.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.NgoOther;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.NgoMembershipDto;
import org.ng.undp.vdms.dto.NgoOtherDto;
import org.ng.undp.vdms.repositories.NgoOtherRepository;
import org.ng.undp.vdms.utils.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class NgoOtherService {

    @Autowired
    private NgoOtherRepository repository;

    @Autowired
    private NgoMembershipService membershipService;

    private NgoOther findByUser(User user){
        return repository.findByUser(user);
    }

    public NgoOther save(NgoOther other){
        return repository.save(other);
    }

    public NgoOther createNgoOther(NgoOtherDto dto) {

        ModelMapper mapper =  new ModelMapper();
        NgoOther other =  mapper.map(dto, NgoOther.class);

        Optional<User> optional = Auth.INSTANCE.getAuth();

        if (!optional.isPresent()) return null;

        User user = optional.get();

        other.setUser(user);

        this.save(other);

        Gson gson =  new Gson();

        NgoMembershipDto[] membershipDtos =
                gson.fromJson(dto.getNgoMembershipArray(),
                        NgoMembershipDto[].class);

        if(membershipDtos != null){

            for(NgoMembershipDto membershipDto : membershipDtos)
                membershipService.createMembership(membershipDto, other, user);
        }


        return other;
    }

    public NgoOtherDto populateOther(User user) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(
                PropertyNamingStrategy.LOWER_CAMEL_CASE);

        ModelMapper modelMapper =  new ModelMapper();

        NgoOther other =  this.findByUser(user);

        NgoOtherDto dto =  null;

        String membershipArray =  null;


        if(!Objects.isNull(other)){

            dto =  modelMapper.map(other,NgoOtherDto.class);

            try {

                membershipArray = objectMapper.writeValueAsString(
                        other.getNgoMemberships().toArray());

                dto.setNgoMembershipArray(membershipArray);
            }catch (JsonProcessingException e){
                e.printStackTrace();

            }
        }

        return dto;
    }
}
