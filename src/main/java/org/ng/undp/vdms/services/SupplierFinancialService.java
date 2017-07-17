package org.ng.undp.vdms.services;

import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.SupplierFinancial;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.SupplierFinancialDto;
import org.ng.undp.vdms.repositories.SupplierFinancialRepository;
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
public class SupplierFinancialService {

    @Autowired
    private SupplierFinancialRepository repository;

    public SupplierFinancial save(SupplierFinancial financial){
        return repository.save(financial);
    }

    public SupplierFinancial findByUser(User user){
        return repository.findByUser(user);
    }

    public SupplierFinancial createSupplierFinancial(SupplierFinancialDto dto){

        ModelMapper modelMapper = new ModelMapper();

        SupplierFinancial financial = modelMapper.map(dto, SupplierFinancial.class);

        Optional<User> optional = Auth.INSTANCE.getAuth();

        if (!optional.isPresent()) {
            return null;
        }

        User user = optional.get();

        financial.setUser(user);

        this.save(financial);

        return financial;
    }

    public SupplierFinancialDto populateFinancial(User user) {

        ModelMapper mapper =  new ModelMapper();
        SupplierFinancialDto dto = null;

        SupplierFinancial financial = this.findByUser(user);

        if(!Objects.isNull(financial))
            dto = mapper.map(financial, SupplierFinancialDto.class);

        return dto;
    }
}
