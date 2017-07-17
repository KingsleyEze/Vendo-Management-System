package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.Vendor;
import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.repositories.VendorRepository;
import org.ng.undp.vdms.repositories.VpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by macbook on 5/1/17.
 */

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;


    public List<Vendor> findAll() {
        return vendorRepository.findAll();
    }

    public List<Vendor> findAllByUser(User u) {
        return vendorRepository.findAllByUser(u);
    }
    @Transactional
    public Vendor findOneByUser(User u) {
        return vendorRepository.findOneByUser(u);
    }

    public Vendor getVpa(String  uuid) {
        return vendorRepository.findOneByUuid(uuid);
    }

    public Vendor findOneByUuid(String  uuid) {
        return vendorRepository.findOneByUuid(uuid);
    }



    public Vendor findOneById(Long  uuid) {
        return vendorRepository.findOneByid(uuid);
    }
    public Vendor editVpa(Vendor vpa) {
        return vendorRepository.save(vpa);
    }

    public void deleteByUuid(String uuid) {
        vendorRepository.deleteByUuid(uuid);
    }

    public Vendor save(Vendor d) {
        return vendorRepository.save(d);
    }
    public Iterable<Vendor> save(List<Vendor> d) {
        return vendorRepository.save(d);
    }

    public  List<Vendor> findAllById(List<String> uuids){return  vendorRepository.findAllByUuid(uuids);}

    public void deleteVendorById(Long id) {
      Vendor user = vendorRepository.findOneByid(id);
        deleteVendor(user);
    }
    public void deleteVendorByUuid(String  uuid) {
         Vendor user = vendorRepository.findOneByUuid(uuid);
        deleteVendor(user);
    }
    public void deleteVendor(Vendor user) {
        if (!Objects.isNull(user)) {
            user.setName(user.getName() + "-deleted" + new Date().toString());

            user.setDeleted_at(new Date());
         vendorRepository.save(user);
        }
    }
}
