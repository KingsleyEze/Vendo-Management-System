package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.SupplierOffice;
import org.ng.undp.vdms.domains.SupplierTechnical;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.SupplierOfficeDto;
import org.ng.undp.vdms.repositories.SupplierOfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/28/2017.
 */

@Service
public class SupplierOfficeService {

    @Autowired
    private SupplierOfficeRepository repository;

    public SupplierOffice save(SupplierOffice office){
        return repository.save(office);
    }

    public SupplierOffice createSupplierOffice(SupplierOfficeDto dto, SupplierTechnical technical, User user) {

        SupplierOffice office = new SupplierOffice();

        if(dto.getId() != null)
            office.setId(dto.getId());

            office.setSupplierTechnical(technical);
            office.setUser(user);
            office.setCompany(dto.getCompany());
            office.setCountry(dto.getCountry());

        return this.save(office);
    }
}
