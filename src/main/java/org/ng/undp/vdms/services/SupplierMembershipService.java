package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.SupplierMembership;
import org.ng.undp.vdms.domains.SupplierOther;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.SupplierMembershipDto;
import org.ng.undp.vdms.repositories.SupplierMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class SupplierMembershipService {

    @Autowired
    private SupplierMembershipRepository repository;

    public SupplierMembership save(SupplierMembership membership){

        return repository.save(membership);
    }

    public SupplierMembership createMembership(
            SupplierMembershipDto membershipDto, SupplierOther other, User user) {

        SupplierMembership membership =  new SupplierMembership();

        if(membershipDto.getId() != null){
            membership.setId(membershipDto.getId());
        }
        membership.setOrganisationName(membershipDto.getOrganisationName());
        membership.setSupplierOther(other);
        membership.setUser(user);

        return this.save(membership);
    }
}
