package org.ng.undp.vdms.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.SupplierOther;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.SupplierMembershipDto;
import org.ng.undp.vdms.dto.SupplierOtherDto;
import org.ng.undp.vdms.repositories.SupplierOtherRepository;
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
public class SupplierOtherService {

    @Autowired
    private SupplierOtherRepository repository;

    @Autowired
    private SupplierMembershipService membershipService;

    private SupplierOther findByUser(User user){
        return repository.findByUser(user);
    }

    public SupplierOther save(SupplierOther other){
        return repository.save(other);
    }

    public SupplierOther createSupplierOther(SupplierOtherDto dto) {

        ModelMapper mapper =  new ModelMapper();
        SupplierOther other =  mapper.map(dto, SupplierOther.class);

        Optional<User> optional = Auth.INSTANCE.getAuth();

        if (!optional.isPresent()) return null;

        User user = optional.get();

        other.setUser(user);

        this.save(other);

        Gson gson =  new Gson();

        SupplierMembershipDto[] membershipDtos =
                gson.fromJson(dto.getSupplierMembershipArray(),
                        SupplierMembershipDto[].class);

        if(membershipDtos != null){

            for(SupplierMembershipDto membershipDto : membershipDtos)
                membershipService.createMembership(membershipDto, other, user);
        }


        return other;
    }

    public SupplierOtherDto populateOther(User user) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(
                PropertyNamingStrategy.LOWER_CAMEL_CASE);

        ModelMapper modelMapper =  new ModelMapper();

        SupplierOther other =  this.findByUser(user);

        SupplierOtherDto dto =  null;

        String membershipArray =  null;


        if(!Objects.isNull(other)){

            dto =  modelMapper.map(other,SupplierOtherDto.class);

            try {

                membershipArray = objectMapper.writeValueAsString(
                        other.getSupplierMemberships().toArray());

                dto.setSupplierMembershipArray(membershipArray);
            }catch (JsonProcessingException e){
                e.printStackTrace();

            }
        }

        return dto;
    }
}
