package org.ng.undp.vdms.services;

import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.SupplierCompany;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.SupplierCompanyDto;
import org.ng.undp.vdms.repositories.SupplierCompanyRepository;
import org.ng.undp.vdms.utils.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/18/2017.
 */

@Service
@Transactional
public class SupplierCompanyService {

    @Autowired
    private SupplierCompanyRepository repository;

    public SupplierCompany save(SupplierCompany supplierCompany){
       return repository.save(supplierCompany);
    }

    public SupplierCompany findByUser(User user){
        return repository.findByUser(user);
    }

    public SupplierCompany createSupplierCompany(SupplierCompanyDto supplierCompanyDto){

        ModelMapper modelMapper = new ModelMapper();

        SupplierCompany supplierCompany = modelMapper.map(supplierCompanyDto, SupplierCompany.class);

        Optional<User> optional = Auth.INSTANCE.getAuth();

        if (!optional.isPresent()) {
            return null;
        }

        User user = optional.get();

        supplierCompany.setUser(user);

        this.save(supplierCompany);

        return supplierCompany;
    }

    public SupplierCompanyDto populateCompany(User user) {

        ModelMapper mapper =  new ModelMapper();
        SupplierCompanyDto dto = null;

        SupplierCompany company = this.findByUser(user);

        if(!Objects.isNull(company))
          dto = mapper.map(company, SupplierCompanyDto.class);

        return dto;
    }
}
