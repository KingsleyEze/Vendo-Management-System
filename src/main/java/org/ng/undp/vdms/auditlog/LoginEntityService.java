package org.ng.undp.vdms.auditlog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by abdulhakim on 11/19/16.
 */
@Service
public  class  LoginEntityService {

    @Autowired
    private  LoginEntityRepository loginEntityRepository;

    public LoginEntity save(LoginEntity loginEntity){
        return loginEntityRepository.save(loginEntity);

    }

    public LoginEntity findByUsername(String username){
        return loginEntityRepository.findByUsername(username);

    }
    public List<LoginEntity> findAll(){
        return ((List<LoginEntity>) loginEntityRepository.findAll());

    }


}
