package org.ng.undp.vdms.customannotations;

/**
 * Created by abdulhakim on 11/19/16.
 */


import org.ng.undp.vdms.customannotations.constants.AuditingActionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})

public @interface Auditable {
    AuditingActionType actionType();
        Class targetEntity() default void.class;


}
