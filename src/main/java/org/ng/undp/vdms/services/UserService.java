package org.ng.undp.vdms.services;


import org.apache.commons.collections4.CollectionUtils;
import org.ng.undp.vdms.auditlog.LoginEntity;
import org.ng.undp.vdms.auditlog.LoginEntityService;
import org.ng.undp.vdms.configs.AppConfigProperties;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.constants.Env;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.dto.CustomUserDetails;
import org.ng.undp.vdms.repositories.UserRepository;
import org.ng.undp.vdms.services.impl.SecurityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by abdulhakim on 10/13/16.
 */

@Component
public class UserService implements UserDetailsService {


    @Autowired
    private AuthenticationManager authenticationManager;


    private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);


    private final UserRepository repository;

    @Autowired
    private LoginEntityService loginEntityService;
    @Autowired
    protected AppConfigProperties properties;

    @Autowired
    public UserService(UserRepository repository, LoginEntityService ls) {
        this.repository = repository;
        this.loginEntityService = ls;
    }

    @Autowired
    private EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        org.ng.undp.vdms.domains.User user = this.repository.findOneByUsername(username);
/*
        return new User(user.getUsername(), user.getPassword(),
                AuthorityUtils.createAuthorityList(user.getUserTypes()));*/

        Set<Role> rolesSet = user.getRoles();
        if (CollectionUtils.isEmpty(rolesSet)) {
            return new User(user.getUsername(), user.getPassword(),
                    AuthorityUtils.createAuthorityList());
        }

        String[] roles = new String[user.getRoles().size()];
        List<String> roleList = new ArrayList<>();
        for (Role r : user.getRoles()) {
            roleList.add(r.getName());
        }

        CustomUserDetails userDetails = new CustomUserDetails(user,
                true, true, true, true,
                AuthorityUtils.createAuthorityList(roleList.toArray(roles)));
        userDetails.setUser(user);

        return userDetails;
    }

    public List<org.ng.undp.vdms.domains.User> findAllByUserTypes(String[] userType) {
        return repository.findAllByUserTypes(userType);
    }

    public List<org.ng.undp.vdms.domains.User> findAllByRoles(Set<Role> userType) {
        return repository.findAllByRoles(userType);
    }

    public List<org.ng.undp.vdms.domains.User> findAllByRoles(String[] userType) {
        return repository.findAllByRoles(userType);
    }

    public Iterable<org.ng.undp.vdms.domains.User> getAllUsers() {

        return this.repository.findAll();
    }

    public org.ng.undp.vdms.domains.User save(org.ng.undp.vdms.domains.User user) {

        return repository.save(user);
    }

    public void updateLastLoggedIn(LocalDateTime lastLogg, String username) {
        // Clear the entity manager cache
        em.clear();
        repository.setFixedLastLoggedIn(lastLogg, username);

        // repository.save(userToSave);
    }

    public org.ng.undp.vdms.domains.User getUserById(long id) {

        return this.repository.findOne(id);
    }

    public org.ng.undp.vdms.domains.User getUserByUuid(String uuid) {

        return this.repository.findOneByUuid(uuid);
    }

    public org.ng.undp.vdms.domains.User getUserByEmail(String email) {

        return repository.findByEmail(email);
    }

    public org.ng.undp.vdms.domains.User getUserByResetPasswordToken(String resetPasswordToken) {

        return repository.findByResetPasswordToken(resetPasswordToken);
    }


    public List<org.ng.undp.vdms.domains.User> getAllActiveUsers() {
        return Accessor.findList(org.ng.undp.vdms.domains.User.class, Filter.get().field("deleted_at").isNull());
    }


    public org.ng.undp.vdms.domains.User updateUser(org.ng.undp.vdms.domains.User user) {
        return repository.save(user);
    }

    public org.ng.undp.vdms.domains.User getByUsername(String username) {
        return repository.findOneByUsername(username);
    }

    public void deleteUserById(Long id) {
        org.ng.undp.vdms.domains.User user = repository.findOne(id);
        deleteUser(user);
    }

    public void deleteUserByUuid(String uuid) {
        org.ng.undp.vdms.domains.User user = repository.findOneByUuid(uuid);
        deleteUser(user);
    }

    public void deleteUser(org.ng.undp.vdms.domains.User user) {
        if (!Objects.isNull(user)) {
            user.setUsername(user.getUsername() + "-deleted" + new Date().toString());
            user.setEmail(user.getEmail() + "-deleted" + new Date().toString());
            user.setDeleted_at(new Date());
            repository.save(user);
        }
    }


    public String findLoggedInUsername() {
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails) userDetails).getUsername();
        }

        return null;
    }


    public void autoLogin(String username, HttpServletRequest request) {


        SecurityContextHolder.clearContext();
        UserDetails userDetails = this.loadUserByUsername(username);
        logger.info(userDetails.getAuthorities().toString());
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        // generate session if one doesn't exist
        request.getSession();
       usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetails(request));

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            logger.debug(String.format("Auto login %s successfully!", username));
        }
        request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
        // creates context for that session.

    }

    public Env getEnvironmentProfile() {
        return Env.valueOf(properties.getActiveProfile().toUpperCase());
    }

}