package org.ng.undp.vdms.utils;

import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.security.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.security.Principal;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Created by emmanuel on 12/1/16.
 * Singleton class to manage Custom Authentication and Authorization Utilities
 * This Singleton is implemented using an Enum to achieve Thread-Safety
 */
public enum Auth {

    INSTANCE; //This enum should have only one instance, because it is not a regular enum

    /*private Auth(){

    }

    private static Auth auth;

    static{
        auth = new Auth();
    }


    public static Auth getInstance(){
        return auth;
    }
*/



    /**
     * Get an Instance of the User Logged In
     * @return
     */
    public Optional<User> getAuth() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || Objects.equals("anonymousUser", authentication.getPrincipal())){
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) principal;

        User appUser = Accessor.findOne(User.class, Filter.get().field("username", user.getUsername()));

        return Optional.ofNullable(appUser);
    }


    /**
     * Check if the user making a Request is loggedIn
     * @return
     */
    public boolean isLoggedIn() {
        return getAuth().isPresent();
    }

    /**
     * Generate Method name that will be used to identify a particular method in
     * Method Security entity/ Table
     * @param m
     * @return
     */
    public String generateMethodNameForSecurity(Method m){
        StringBuilder sb = new StringBuilder(m.getDeclaringClass().getCanonicalName());
        sb.append("." + m.getName());//method Name
        sb.append(":");//apppend ":" to mark the start of parameter listing
        for (Class clazz : m.getParameterTypes()){
            sb.append(clazz.getSimpleName());
        }

        return sb.toString();
    }

    /**
     * only returns a School if the user logged in is a school owner
     * @return
     */


    public boolean hasAnyPerm(Set<Permission> perms) {
        Optional<User> t = getAuth();
        if(t.isPresent()) {
            Set<Permission> permissions = t.get().getPermissions();
            return isLoggedIn() && (perms.stream().anyMatch(perm -> permissions.contains(perm)));
        }

        return false;
    }


    public boolean hasAnyPerm(Permission perm) {

        if(!isLoggedIn()){
            return false;
        }

        Optional<User> t = getAuth();
        if(t.isPresent()) {
            Set<Permission> permissions = t.get().getPermissions();
            for(Permission p : permissions){
                if(perm.equals(p)){
                    return true;
                }
            }
        }

        return false;
    }

    public boolean hasAllPerm(Set<Permission> perms) {
        Optional<User> t = getAuth();
        if(t.isPresent()) {
            Set<Permission> permissions = t.get().getPermissions();
            return isLoggedIn() && (perms.stream().allMatch(perm -> permissions.contains(perm)));
        }

        return false;
    }
}
