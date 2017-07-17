package org.ng.undp.vdms.services.settings;

import org.ng.undp.vdms.domains.settings.Agency;
import org.ng.undp.vdms.domains.settings.Department;
import org.ng.undp.vdms.repositories.settings.AgencyRepository;
import org.ng.undp.vdms.repositories.settings.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by macbook on 6/26/17.
 */

@Service
public class AgencyService {
    @Autowired
  private  AgencyRepository agencyRepository;





    public Iterable<Agency> findAll() {

        return agencyRepository.findAll();
    }



    public Agency getDepartment(Long id) {
        return agencyRepository.findOne(id);
    }

    public Agency editVpa(Agency vpa) {
        return agencyRepository.save(vpa);
    }

    public Agency save(Agency d) {
        return agencyRepository.save(d);
    }

    public void delete(Long id) {
        agencyRepository.delete(id);
    }


    public List<Agency> findAllById(List<Long> DepartmentIds) {
        return agencyRepository.findAllById(DepartmentIds);
    }

}
