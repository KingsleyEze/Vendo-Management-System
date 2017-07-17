package org.ng.undp.vdms.services;

import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.NgoFinancial;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.NgoFinancialDto;
import org.ng.undp.vdms.repositories.NgoFinancialRepository;
import org.ng.undp.vdms.utils.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/18/2017.
 */

@Service
public class NgoFinancialService {

    @Autowired
    private NgoFinancialRepository repository;

    public NgoFinancial save(NgoFinancial financial){
        return repository.save(financial);
    }

    public NgoFinancial findByUser(User user){
        return repository.findByUser(user);
    }

    public NgoFinancial createNgoFinancial(NgoFinancialDto dto){

        ModelMapper modelMapper = new ModelMapper();

        NgoFinancial financial = modelMapper.map(dto, NgoFinancial.class);

        Optional<User> optional = Auth.INSTANCE.getAuth();

        if (!optional.isPresent()) {
            return null;
        }

        User user = optional.get();

        financial.setUser(user);

        this.save(financial);

        return financial;
    }

    public NgoFinancialDto populateFinancial(User user) {

        ModelMapper mapper =  new ModelMapper();
        NgoFinancialDto dto = null;

        NgoFinancial financial = this.findByUser(user);

        if(!Objects.isNull(financial))
            dto = mapper.map(financial, NgoFinancialDto.class);

        return dto;
    }
}
