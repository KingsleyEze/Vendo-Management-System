package org.ng.undp.vdms.configs;

import org.ng.undp.vdms.controllers.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * Created by macbook on 6/18/17.
 */
@Component
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public CustomLoginSuccessHandler() {

        super();

        String defaultTargetUrl = "/showCriteria";
        setDefaultTargetUrl(defaultTargetUrl);

    }


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession();

        authentication.getAuthorities().forEach(n -> {
            logger.info(n.getAuthority());
            if (n.getAuthority().equals("ADMIN_ACCOUNT") || n.getAuthority().equals("STAFF") ||n.getAuthority().equals("UNSTAFF"))
            {super.setDefaultTargetUrl("/accounts");}
            else{
                super.setDefaultTargetUrl("/showCriteria");

            }


             });

        if (session != null) {
            String redirectUrl = (String) session.getAttribute("url_prior_login");


            if (redirectUrl != null) {
                // we do not forget to clean this attribute from session
                session.removeAttribute("url_prior_login");
                // then we redirect
                getRedirectStrategy().sendRedirect(request, response, redirectUrl);
            } else {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        } else {
            String errorMessage = "Invalid username or password";
            System.out.println("Login success handler block " + errorMessage);

            // getRedirectStrategy().sendRedirect(request, response,"/accounts/login?error=" + errorMessage);

            super.onAuthenticationSuccess(request, response, authentication);
        }
    }


}
