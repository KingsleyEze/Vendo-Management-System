package org.ng.undp.vdms.configs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by macbook on 7/5/17.
 */
public class SessionTimerInterceptor extends HandlerInterceptorAdapter {

    private  final Logger  log = LoggerFactory.getLogger(this.getClass());

    /* (60  X 1000) == 60 secs ==  1min */
    private static final long MAX_INACTIVE_SESSION_TIME = 15 * (60) * 1000;

    @Autowired
    private HttpSession session;


    @Override
    public boolean preHandle(
            HttpServletRequest req, HttpServletResponse response, Object handler) throws Exception {
        log.info("Pre handle method - check handling start time");
        long startTime = System.currentTimeMillis();
        req.setAttribute("executionTime", startTime);

        if (UserInterceptor.isUserLogged()) {
            session = req.getSession();
            log.info("Time since last request in this session: {} ms",
                    System.currentTimeMillis() - req.getSession().getLastAccessedTime());
            if (System.currentTimeMillis() - session.getLastAccessedTime()
                    > MAX_INACTIVE_SESSION_TIME) {
                log.warn("Logging out, due to inactive session");
                SecurityContextHolder.clearContext();
                req.logout();
                String errorMessage =  "Logging out, due to inactive session";
                response.sendRedirect("/logout?"+ errorMessage);
            }
        }
 return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            ModelAndView model) throws Exception {
        log.info("Post handle method - check execution time of handling");
        long startTime = (Long) request.getAttribute("executionTime");
        log.info("Execution time for handling the request was: {} ms",
                System.currentTimeMillis() - startTime);
    }
}
