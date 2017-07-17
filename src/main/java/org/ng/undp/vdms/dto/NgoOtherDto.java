package org.ng.undp.vdms.dto;

import lombok.Data;
import org.ng.undp.vdms.domains.NgoMembership;

import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Data
public class NgoOtherDto {

    private Long id;

    private String ngoDispute1;

    private String ngoDispute2;

    private String ngoDispute3;

    private String functionalTitle;

    private String signature;

    private String ngoMembershipArray;

    private List<NgoMembership> ngoMemberships;
}
