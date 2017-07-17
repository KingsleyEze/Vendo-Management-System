package org.ng.undp.vdms.customannotations.aspects;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.ng.undp.vdms.customannotations.Auditable;
import org.ng.undp.vdms.customannotations.AuditingEntity;
import org.ng.undp.vdms.customannotations.AuditingRepository;
import org.ng.undp.vdms.customannotations.AuditingTargetUsername;
import org.ng.undp.vdms.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.security.Principal;

/**
 * Created by abdulhakim on 11/19/16.
 */


@Aspect
public class AuditingAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserService userService;
    @Autowired
    AuditingRepository auditingRepository;


    /*   AuditingEntity(String username, String user_type, String action, String target_user, String user_ip)*/




    //@After("@annotation(auditable)")
    @After("@annotation(auditable)")
    @Transactional
    public void logAuditActivity(JoinPoint jp, Auditable auditable, HttpServletRequest request, Principal principal) {
        String targetAuditingUser = "Nonene";
        String actionType = auditable.actionType().toString(); //.getDescription();
AuditingTargetUsername auditingTargetUsername ;


        Authentication auth = ((Authentication) principal);


        String auditingUsername = auth.getName();

        String role = "User";
        //List<String> role =
        String auditingUsernameIp = request.getRemoteAddr();




    logger.info(
            "Auditing information. auditingUsername=" + auditingUsername +", actionType="+ actionType +", role=" + role + ", targetAuditingUser="
            + targetAuditingUser + " auditingUsernameIp=" + auditingUsernameIp);

 auditingRepository.save(new AuditingEntity(auditingUsername, role, actionType, targetAuditingUser, auditingUsernameIp));

}

    private String getTargetAuditingUserViaAnnotation(Object obj) {
        Class cl =obj.getClass();
        String result = null;
        try {
            for (java.lang.reflect.Field f : cl.getDeclaredFields())
                for (Annotation a : f.getAnnotations()) {
                    if (a.annotationType() == AuditingTargetUsername.class) {
                        f.setAccessible(true);
                        Field annotatedFieldName = cl.getDeclaredField(f.getName());
                        annotatedFieldName.setAccessible(true);
                        String annotatedFieldVal = (String) annotatedFieldName.get(obj);
                        logger.debug("Found auditing annotation. type=" + a.annotationType() + " value=" + annotatedFieldVal.toString());
                        result = annotatedFieldVal;
                    }
                }
        } catch (Exception e) {
            logger.error("Error extracting auditing annotations from obj" + obj.getClass());
        }
        return result;
    }




}