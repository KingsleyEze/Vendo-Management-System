package org.ng.undp.vdms.security;

import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.domains.security.PathSecurity;
import org.ng.undp.vdms.domains.security.Permission;
import org.ng.undp.vdms.utils.Auth;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Created by emmanuel on 12/1/16.
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        Class<?> clazz = method.getDeclaringClass();

        String bestMatchPattern = (String ) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String resource = bestMatchPattern + ":" + request.getMethod();
        //System.out.println("The resource is " + resource);

        Permission p = getResourcePermission(resource);
        if(Objects.isNull(p)){
            return true;//unprotected resource, allow access
        }

        if (Auth.INSTANCE.isLoggedIn()) {
            return authorize(p, response);
        } else {
            response.sendRedirect("/accounts/login");
            return false;
        }

    }


    private Permission getResourcePermission(String resource){
        if(StringUtils.isNotEmpty(resource)){
            resource = resource.toLowerCase();
        }

        PathSecurity security = Accessor.findOne(PathSecurity.class, "name", resource);
        if(security == null){
            return null;
        }

        return security.getPermission();
    }

    public boolean authorize(Permission perm, HttpServletResponse response) throws Exception {
        //System.out.println(perm);
        if(Auth.INSTANCE.hasAnyPerm(perm)){
            //System.out.println("Returns true from check Perm");
            return true;
        }
        String msg = "You do not have the required permission to perform that action!";
        response.sendRedirect("/status/not-authorized?msg=" + msg);

        return false;
    }

/*
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod hm = (HandlerMethod) handler;
        Method method = hm.getMethod();
        Class<?> clazz = method.getDeclaringClass();

        //request.getRequestURI();

        if(method.isAnnotationPresent(Security.class)) {
            if (Auth.INSTANCE.isLoggedIn()) {
                return checkPerm(method, response);
            } else {
                response.sendRedirect("/login");
                return false;
            }
        } else if(clazz.isAnnotationPresent(Security.class)){
            if (Auth.INSTANCE.isLoggedIn()) {
                return checkPerm(clazz, response);
            }
            else {
                response.sendRedirect("/login");
                return false;
            }
        }
        return true;
    }

    public boolean checkPerm(Class clazz, HttpServletResponse response) throws IOException {
        System.out.println("Class checkPerm");
        ClassSecurity sec = Accessor.findOne(ClassSecurity.class, Filter.get().field("name", clazz.getCanonicalName()));

        if(Objects.isNull(sec)){//return true if no security record is created for this Class
            return true;
        }
        return checkPerm(sec.getPermissions());
    }

    public boolean checkPerm(Method method, HttpServletResponse response) throws IOException {
        System.out.println("Method checkPerm");
        MethodSecurity sec = Accessor.findOne(MethodSecurity.class, Filter.get().field("method", Auth.INSTANCE.generateMethodNameForSecurity(method) ));

        if(Objects.isNull(sec)){//Method not secured at db level
            return true;
        }


        return checkPerm(sec.getPermissions());
    }

    public boolean checkPerm(Set<Permission> perms) {
        System.out.println(perms);
        if(Auth.INSTANCE.hasAnyPerm(perms)){
            System.out.println("Returns true from check Perm");
            return true;
        }
        String msg = "YOU DO NOT HAVE THE REQUIRED PERMISSION TO PERFORM THAT ACTION!";
        //response.sendRedirect("/accounts?msg=" + msg);
        System.out.println(msg);

        return false;
    }
*/
}
