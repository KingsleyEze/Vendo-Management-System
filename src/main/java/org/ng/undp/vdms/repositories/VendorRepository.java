package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.Vendor;
import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.security.Role;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by macbook on 5/1/17.
 */
public interface VendorRepository  extends CrudRepository<Vendor, String>, JpaSpecificationExecutor<Vendor> {

public Vendor findOneByUuid(String uuid);
    public Vendor findOneByUser(User uuid);
public void deleteByUuid(String uuid);
    public Vendor findOneByid(Long id);
    public List<Vendor> findAll();

public List<Vendor> findAllByVpa(Vpa vpa);
    public List<Vendor> findAllByUser(User user);

public List<Vendor> findAllByUuid(List<String> uuid);


}
