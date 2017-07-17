package org.ng.undp.vdms.configs;

import org.ng.undp.vdms.auditlog.LoginEntity;
import org.ng.undp.vdms.auditlog.LoginEntityService;
import org.ng.undp.vdms.controllers.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Date;

/**
 * Created by abdulhakim on 11/19/16.
 */
//@Component
public class CustomLogoutHandler extends BaseController implements LogoutHandler {
    @Autowired
    private LoginEntityService loginEntityService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LoginEntity loginEntity =loginEntityService.findByUsername( this.loggedInUser((Principal) authentication.getPrincipal()).getUser().getUsername());


        loginEntity.setLogged_out(new Date());
        loginEntityService.save(loginEntity);

        try {
            request.logout();
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }
}