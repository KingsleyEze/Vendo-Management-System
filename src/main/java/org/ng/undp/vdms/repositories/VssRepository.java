package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.Vss;
import org.ng.undp.vdms.domains.constants.UserType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by abdulhakim on 11/12/16.
 */
public  interface VssRepository  extends CrudRepository<Vss, Long>, JpaSpecificationExecutor<Vss> {

    public List<Vss> findAllByUsertypeAndVpa(UserType userType, Vpa vpa);



    @Query("SELECT q FROM Vss q WHERE    q.vpa.id in (:vpaIds) AND q.usertype=:usertype")

    public List<Vss> findAllByVpa(@Param("vpaIds") List<Long> vpaIds, @Param("usertype") UserType userType);


    @Query("SELECT q FROM Vss q WHERE  q.id IN (:vssIds)")
    public List<Vss> findAllById(@Param("vssIds") List<Long> vssIds);




}
