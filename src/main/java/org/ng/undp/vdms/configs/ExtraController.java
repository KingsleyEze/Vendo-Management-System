package org.ng.undp.vdms.configs;

import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.Vendor;
import org.ng.undp.vdms.repositories.UserRepository;
import org.ng.undp.vdms.services.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

/**
 * Created by macbook on 6/22/17.
 */

@ControllerAdvice

class ExtraController {
    @Autowired
    VendorService vendorService;

    @Autowired
    UserRepository userRepository;

    @ModelAttribute
    public void addAttributes(Principal principal, Model model) {
       // org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User appUser=null;
        if(principal != null)
        { appUser = userRepository.findOneByUsername(principal.getName());}

        Vendor thematicSize = vendorService.findOneByUser(appUser);
        model.addAttribute("thematicSize", thematicSize);


    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setBindEmptyMultipartFiles(false);
    }
}
