package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Country;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * Created by abdulhakim on 12/6/16.
 */


public interface  CountryRepository extends CrudRepository<Country, Long>, JpaSpecificationExecutor<Country> {

    @Query("SELECT p FROM Country p WHERE  p.name like CONCAT(:name,'%')")
    public Country findByNameLike(@Param("name") final String name);


    public Country findByName(String name);

}
