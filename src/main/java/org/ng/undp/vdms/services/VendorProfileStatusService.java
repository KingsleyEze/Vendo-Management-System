package org.ng.undp.vdms.services;

import org.ng.undp.vdms.repositories.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by macbook on 6/25/17.
 */

@Service
public class VendorProfileStatusService {

    @Autowired
   private  VendorRepository vendorRepository;
    @Autowired
    private  VendorService vendorService;
    public boolean checkVendorProfileStatus( String uuid){
        return true;

    }

    public boolean checkVendorProfileStatus( Long id){
        return false;

    }

}
