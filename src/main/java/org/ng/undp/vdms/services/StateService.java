package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.Country;
import org.ng.undp.vdms.domains.State;
import org.ng.undp.vdms.repositories.CountryRepository;
import org.ng.undp.vdms.repositories.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abdulhakim on 12/6/16.
 */
@Service
public class StateService  {
    @Autowired
    StateRepository stateRepository;
    @Autowired
    CountryRepository countryRepository;

    public State save(State state){
        return stateRepository.save(state);
    }

    public List<State> findAllByCountry(String country){
        Country country1 = countryRepository.findByName(country);
        return  stateRepository.findAllByCountry(country1);

    }

    public Iterable<State> findAll(){
        return stateRepository.findAll();
    }


    public  State findById(Long id){
        return  stateRepository.findOne(id);
    }

    public  State findByName(String name){
        return  stateRepository.findByName(name);
    }
}
