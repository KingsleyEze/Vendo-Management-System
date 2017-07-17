package org.ng.undp.vdms.domains.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by emmanuel on 12/9/16.
 */
public enum Env {
    PROD, DEV, SCHOOL;
    
    public static List<Env> asList(){
        return Arrays.asList(Env.values());
    }
}


