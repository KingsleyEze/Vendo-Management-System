package org.ng.undp.vdms.services.security;

import org.ng.undp.vdms.domains.security.MethodSecurity;
import org.ng.undp.vdms.repositories.security.MethodSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by emmanuel on 12/2/16.
 */
@Service("methodSecurityService")
public class MethodSecurityService {

    @Autowired
    private MethodSecurityRepository repo;

    public Iterable<MethodSecurity> findAll() {
        return repo.findAll();
    }

    public MethodSecurity findById(Long id) {
        return repo.findOne(id);
    }

    public MethodSecurity findByClazz(Long clazz) {
        return repo.findOneByClazz(clazz);
    }

    public MethodSecurity findByMethod(String method) {
        return repo.findOneByMethod(method);
    }

    public MethodSecurity save(MethodSecurity methodSecurity) {
        if (!Objects.isNull(methodSecurity)) {
            methodSecurity = repo.save(methodSecurity);
        }
        return methodSecurity;
    }

    public void deleteById(Long id) {
        repo.delete(id);
    }

    public void delete(MethodSecurity methodSecurity){
        repo.delete(methodSecurity);
    }

}
