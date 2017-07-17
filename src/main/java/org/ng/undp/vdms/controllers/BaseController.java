package org.ng.undp.vdms.controllers;

import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.dto.CustomUserDetails;
import org.ng.undp.vdms.repositories.UserRepository;
import org.ng.undp.vdms.services.UserService;
import org.ng.undp.vdms.services.security.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by abdulhakim on 10/13/16.
 */
public class BaseController {


    @Autowired

    protected UserRepository userRepository;
    @Autowired
    protected UserService userService;

    @Autowired
    protected RoleService roleService;

    protected ArrayList<String> errors = new ArrayList<String>();
    protected ArrayList<String> success = new ArrayList<String>();
    protected ArrayList<String> stickyNotice = new ArrayList<String>();

    private Logger logger = LoggerFactory.getLogger(this.getClass());


    public CustomUserDetails loggedInUser(Principal principal) {

        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User appUser = userRepository.findOneByUsername(user.getUsername());

        String[] roles = new String[appUser.getRoles().size()];
        List<String> roleList = new ArrayList<>();
        for (Role r : appUser.getRoles()) {
            roleList.add(r.getName());
        }

        CustomUserDetails userDetails = new CustomUserDetails(appUser,
                true, true, true, true,
                AuthorityUtils.createAuthorityList(roleList.toArray(roles)));

        userDetails.setUser(appUser);


        return userDetails;
    }

    public String redirectVendor(CustomUserDetails loggedinUser) {
        String consultantAccountRole = UserType.CONSULTANT.toString();
        String ngoAdminAccountRole = UserType.NGO.toString();
        String supplierAccountRole = UserType.SUPPLIER.toString();
        String vendorAccountRole = UserType.VENDOR.toString();


        Set<String> userRoles = loggedinUser.getUser().getRoleNames();


        if (userRoles.contains(consultantAccountRole)) {
            //redirect to  consultant vendor form (P11 Form)
            return "redirect:/consultants/addBioData";
        } else if (userRoles.contains(ngoAdminAccountRole)) {

            //redirect to ngo account dashboard
            return "redirect:/ngos/step-one";
        } else if (userRoles.contains(supplierAccountRole)) {
            //redirect to ngo account dashboard
            return "redirect:/suppliers/step-one";
        }
        return "redirect:/accounts";

    }


}
