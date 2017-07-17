package org.ng.undp.vdms.configs;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by macbook on 6/27/17.
 */



@Component
public class FailureLoginHandler implements AuthenticationFailureHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e)
            throws IOException, ServletException {

        System.out.println("Failure login deteted");
        String errorMessage = ExceptionUtils.getMessage(e);

        String referer = request.getHeader("referer");

        // sendError(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED, errorMessage, e);
        System.out.println(errorMessage);
        System.out.println(e.toString());

        // if(e.getClass().isAssignableFrom(DisabledException.class)){
        // setDefaultFailureUrl("/accountRecovery");

        if (e.getClass().isAssignableFrom(BadCredentialsException.class)) {
            System.out.println("If block running...");
            errorMessage = "Invalid username and/or password";

            redirectStrategy.sendRedirect(request, response,"/accounts/login?error=" + errorMessage);
        }

       else if (e.getClass().isAssignableFrom(LockedException.class)) {
            System.out.println("Else if block running...");
            errorMessage = "Your account is locked out or suspended, contact an admin  <a href='/contact'> Contact Support</a>";


            response.sendRedirect("/accounts/login?error=" + errorMessage);
        }

        else{
            System.out.println("Else block running...");
            errorMessage = "Oops something went wrong..., contact an admin  <a href='/contact'> Contact Support</a>";

            response.sendRedirect("/accounts/login?error=" + errorMessage);

        }
    }


    private void sendError(HttpServletResponse response, int code, String message, Exception e) throws IOException {
        SecurityContextHolder.clearContext();

        Response<String> exceptionResponse =
                new Response<>(Response.STATUES_FAILURE, message, ExceptionUtils.getStackTrace(e));

        exceptionResponse.send(response, code);
    }

}
