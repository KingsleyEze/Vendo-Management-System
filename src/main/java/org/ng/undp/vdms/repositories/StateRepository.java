package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Country;
import org.ng.undp.vdms.domains.State;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by abdulhakim on 12/6/16.
 */


public interface  StateRepository extends CrudRepository<State, Long>, JpaSpecificationExecutor<Country> {

    @Query("SELECT p FROM State p WHERE  p.name like CONCAT(:name,'%')")
    public State findByNameLike(@Param("name") final String name);


    public State findByName(String name);

    public List<State> findAllByCountry(Country country);

}
