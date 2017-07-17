package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.NgoMembership;
import org.ng.undp.vdms.domains.NgoOther;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.NgoMembershipDto;
import org.ng.undp.vdms.repositories.NgoMembershipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Service
public class NgoMembershipService {

    @Autowired
    private NgoMembershipRepository repository;

    public NgoMembership save(NgoMembership membership){

        return repository.save(membership);
    }

    public NgoMembership createMembership(
            NgoMembershipDto membershipDto, NgoOther other, User user) {

        NgoMembership membership =  new NgoMembership();

        if(membershipDto.getId() != null){
            membership.setId(membershipDto.getId());
        }
        membership.setOrganisationName(membershipDto.getOrganisationName());
        membership.setNgoOther(other);
        membership.setUser(user);

        return this.save(membership);
    }
}
