package org.ng.undp.vdms.security;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by emmanuel on 12/14/15.
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Security {
    //long[] permissions() default {};//return Id's of a permission
}
