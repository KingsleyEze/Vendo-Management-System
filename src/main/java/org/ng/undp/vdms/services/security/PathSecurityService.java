package org.ng.undp.vdms.services.security;

import org.ng.undp.vdms.domains.security.PathSecurity;
import org.ng.undp.vdms.repositories.security.PathSecurityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Created by emmanuel on 12/2/16.
 */
@Service("pathSecurityService")
public class PathSecurityService {

    @Autowired
    private PathSecurityRepository repo;

    public Iterable<PathSecurity> findAll() {
        return repo.findAll();
    }

    public PathSecurity findById(Long id) {
        return repo.findOne(id);
    }

    public PathSecurity findByName(String name) {
        return repo.findOneByName(name);
    }

    public PathSecurity findByPermission(Long permissionId) {
        return repo.findOneByPermission(permissionId);
    }

    public PathSecurity save(PathSecurity pathSecurity) {
        if (!Objects.isNull(pathSecurity)) {
            pathSecurity = repo.save(pathSecurity);
        }
        return pathSecurity;
    }

    public void deleteById(Long id) {
        repo.delete(id);
    }

    public void delete(PathSecurity pathSecurity){
        repo.delete(pathSecurity);
    }

}
