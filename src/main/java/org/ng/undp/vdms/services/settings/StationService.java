package org.ng.undp.vdms.services.settings;

import org.ng.undp.vdms.domains.settings.Station;
import org.ng.undp.vdms.repositories.settings.StationRepository;
import org.ng.undp.vdms.repositories.settings.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by macbook on 6/26/17.
 */
@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;




    public Iterable<Station> findAll() {

        return stationRepository.findAll();
    }



    public Station getStation(Long id) {
        return stationRepository.findOne(id);
    }

    public Station editVpa(Station vpa) {
        return stationRepository.save(vpa);
    }

    public Station save(Station d) {
        return stationRepository.save(d);
    }

    public void delete(Long id) {
        stationRepository.delete(id);
    }


    public List<Station> findAllById(List<Long> StationIds) {
        return stationRepository.findAllById(StationIds);
    }
}
