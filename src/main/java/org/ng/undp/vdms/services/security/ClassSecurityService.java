package org.ng.undp.vdms.services.security;

import org.ng.undp.vdms.domains.security.ClassSecurity;
import org.ng.undp.vdms.repositories.security.ClassSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by emmanuel on 12/2/16.
 */
@Service("classSecurityService")
public class ClassSecurityService {

    @Autowired
    private ClassSecurityRepository repo;

    public Iterable<ClassSecurity> findAll() {
        return repo.findAll();
    }

    public ClassSecurity findById(Long id) {
        return repo.findOne(id);
    }

    public ClassSecurity findByName(String name) {
        return repo.findOneByName(name);
    }

    public ClassSecurity save(ClassSecurity classSecurity) {
        if (!Objects.isNull(classSecurity)) {
            classSecurity = repo.save(classSecurity);
        }
        return classSecurity;
    }

    public void deleteById(Long id) {
        repo.delete(id);
    }

    public void delete(ClassSecurity classSecurity){
        repo.delete(classSecurity);
    }

}
