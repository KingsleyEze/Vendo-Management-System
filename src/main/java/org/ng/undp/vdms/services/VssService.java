package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.Vss;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.repositories.VpaRepository;
import org.ng.undp.vdms.repositories.VssRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abdulhakim on 11/12/16.
 */

@Service
public class VssService {


    @Autowired
    private VpaRepository vpaRepository;

    @Autowired
    private VssRepository vssRepository;

    public Iterable<Vss> findAll() {

        return vssRepository.findAll();
    }

    public List<Vss> findAllByUsertypeAndVpa(UserType u, Vpa vpa) {
        return vssRepository.findAllByUsertypeAndVpa(u, vpa);
    }

    public List<Vss> findAllByVpa(List<Long> vpaIds, UserType userType) {
        return vssRepository.findAllByVpa(vpaIds, userType);
    }

    public Vss getVss(Long id) {
        return vssRepository.findOne(id);
    }

    public Vss editVpa(Vss vpa) {
        return vssRepository.save(vpa);
    }

    public Vss save(Vss d) {
        return vssRepository.save(d);
    }

    public void delete(Long id) {
        vssRepository.delete(id);
    }


    public List<Vss> findAllById(List<Long> vssIds) {
        return vssRepository.findAllById(vssIds);
    }
}
