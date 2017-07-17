package org.ng.undp.vdms.services;

/**
 * Created by macbook on 3/7/17.
 */
public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username);
}