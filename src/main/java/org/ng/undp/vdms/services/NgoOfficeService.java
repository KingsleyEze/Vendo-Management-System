package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.NgoOffice;
import org.ng.undp.vdms.domains.NgoTechnical;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.NgoOfficeDto;
import org.ng.undp.vdms.repositories.NgoOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/28/2017.
 */

@Service
public class NgoOfficeService {

    @Autowired
    private NgoOfficeRepository repository;

    public NgoOffice save(NgoOffice office){
        return repository.save(office);
    }

    public NgoOffice createNgoOffice(NgoOfficeDto dto, NgoTechnical technical, User user) {

        NgoOffice office = new NgoOffice();

        if(dto.getId() != null)
            office.setId(dto.getId());

            office.setNgoTechnical(technical);
            office.setUser(user);
            office.setCompany(dto.getCompany());
            office.setCountry(dto.getCountry());

        return this.save(office);
    }
}
