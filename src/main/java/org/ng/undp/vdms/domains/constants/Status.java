package org.ng.undp.vdms.domains.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by emmanuel on 6/18/17.
 */
public enum Status {

    PENDING, IN_PROGRESS, CANCELLED, COMPLETED;

    public static List<Status> asList(){return Arrays.asList(Status.values());}
}
