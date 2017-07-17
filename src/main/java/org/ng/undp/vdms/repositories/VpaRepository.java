package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.domains.constants.UserType;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.security.auth.Subject;
import java.util.List;

/**
 * Created by abdulhakim on 11/12/16.
 */
public interface VpaRepository  extends CrudRepository<Vpa, Long>, JpaSpecificationExecutor<Vpa> {

    public List<Vpa> findAllByNameAndUsertype(String name, UserType u);

    public  List<Vpa> findAllByUsertype(UserType u);

    @Query("SELECT q FROM Vpa q WHERE  q.usertype IN (:vpaIds)")

    public  List<Vpa> findAllByUsertype(@Param("vpaIds") List<UserType> u);

    public  Vpa findByNameAndUsertype(String name, UserType u);

    @Query("SELECT q FROM Vpa q WHERE  q.id IN (:vpaIds)")
    public List<Vpa> findAllById(@Param("vpaIds") List<Long> vpaIds);



}
