package org.ng.undp.vdms.domains.constants;

import java.util.Arrays;
import java.util.List;

/**
 * Created by abdulhakim on 7/16/17.
 */
public enum TenderStatus {

    TO_BE_REVIEWED,REVIEWED, UNDER_REVIEW, CANCELLED,WITHDRAWN,WITHDRAWN_BY_BIDDER, SHORTLISTED, SELECTED, ENGAGED;

    public static List<TenderStatus> asList(){return Arrays.asList(TenderStatus.values());}
}
