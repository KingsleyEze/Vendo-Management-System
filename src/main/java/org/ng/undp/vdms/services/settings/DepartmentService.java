package org.ng.undp.vdms.services.settings;

import org.ng.undp.vdms.domains.settings.Department;
import org.ng.undp.vdms.repositories.settings.DepartmentRepository;
import org.ng.undp.vdms.repositories.settings.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by macbook on 6/26/17.
 */

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository  departmentRepository;




    public Iterable<Department> findAll() {

        return departmentRepository.findAll();
    }



    public Department getDepartment(Long id) {
        return departmentRepository.findOne(id);
    }

    public Department editVpa(Department vpa) {
        return departmentRepository.save(vpa);
    }

    public Department save(Department d) {
        return departmentRepository.save(d);
    }

    public void delete(Long id) {
        departmentRepository.delete(id);
    }


    public List<Department> findAllById(List<Long> DepartmentIds) {
        return departmentRepository.findAllById(DepartmentIds);
    }
}
