package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.repositories.VpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abdulhakim on 11/12/16.
 */
@Service
public class VpaService {
    @Autowired
    private VpaRepository vpaRepository;

    public Iterable<Vpa> findAll() {

        return vpaRepository.findAll();
    }


    public List<Vpa> findAllByUsertype(UserType u) {
        return vpaRepository.findAllByUsertype(u);
    }

    public List<Vpa> findAllByUsertype(List<UserType> u) {
        return vpaRepository.findAllByUsertype(u);
    }

    public Vpa getVpa(Long id) {
        return vpaRepository.findOne(id);
    }

    public Vpa editVpa(Vpa vpa) {
        return vpaRepository.save(vpa);
    }

    public void delete(Long id) {
        vpaRepository.delete(id);
    }
    public Vpa save(Vpa d) {
        return vpaRepository.save(d);
    }

    public  List<Vpa> findAllById(List<Long> vpaIds){return  vpaRepository.findAllById(vpaIds);}
}
