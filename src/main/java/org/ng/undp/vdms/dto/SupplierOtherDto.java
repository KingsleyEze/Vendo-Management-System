package org.ng.undp.vdms.dto;

import lombok.Data;
import org.ng.undp.vdms.domains.SupplierMembership;

import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Data
public class SupplierOtherDto {

    private Long id;

    private String supplierDispute1;

    private String supplierDispute2;

    private String supplierDispute3;

    private String functionalTitle;

    private String signature;

    private String supplierMembershipArray;

    private List<SupplierMembership> supplierMemberships;
}
