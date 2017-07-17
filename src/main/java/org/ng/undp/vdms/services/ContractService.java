package org.ng.undp.vdms.services;

import org.apache.commons.collections4.IterableUtils;
import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.repositories.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by emmanuel on 6/10/17.
 */
@Service
public class ContractService {

    @Autowired
    private ContractRepository contractRepository;

    public List<Contract> findAll(){
        return IterableUtils.toList(contractRepository.findAll());
    }


    public Contract save(Contract contract){
        return contractRepository.save(contract);
    }

    public void delete(Contract contract){
        contractRepository.delete(contract);
    }

    public void deleteById(Long id){
        contractRepository.delete(id);
    }
}
