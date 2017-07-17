package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.Country;
import org.ng.undp.vdms.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by abdulhakim on 12/6/16.
*/

 @Service
public class CountryService  {
    @Autowired
    CountryRepository countryRepository ;

    public Country save(Country country){
        return countryRepository.save(country);
    }

    public Iterable<Country> findAll(){
        return countryRepository.findAll();
    }
    public  Country findById(Long id){
        return  countryRepository.findOne(id);
    }

    public  Country findByName(String name){
        return  countryRepository.findByName(name.toUpperCase());
    }
}
