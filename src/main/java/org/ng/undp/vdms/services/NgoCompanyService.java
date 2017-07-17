package org.ng.undp.vdms.services;

import org.modelmapper.ModelMapper;
import org.ng.undp.vdms.domains.NgoCompany;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.NgoCompanyDto;
import org.ng.undp.vdms.repositories.NgoCompanyRepository;
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
public class NgoCompanyService {

    @Autowired
    private NgoCompanyRepository repository;

    public NgoCompany save(NgoCompany ngoCompany){
       return repository.save(ngoCompany);
    }

    public NgoCompany findByUser(User user){
        return repository.findByUser(user);
    }

    public NgoCompany createNgoCompany(NgoCompanyDto ngoCompanyDto){

        ModelMapper modelMapper = new ModelMapper();

        NgoCompany ngoCompany = modelMapper.map(ngoCompanyDto, NgoCompany.class);

        Optional<User> optional = Auth.INSTANCE.getAuth();

        if (!optional.isPresent()) {
            return null;
        }

        User user = optional.get();

        ngoCompany.setUser(user);

        this.save(ngoCompany);

        return ngoCompany;
    }

    public NgoCompanyDto populateCompany(User user) {

        ModelMapper mapper =  new ModelMapper();
        NgoCompanyDto dto = null;

        NgoCompany company = this.findByUser(user);

        if(!Objects.isNull(company))
          dto = mapper.map(company, NgoCompanyDto.class);

        return dto;
    }
}
