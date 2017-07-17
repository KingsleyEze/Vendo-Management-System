package org.ng.undp.vdms.dto;

import lombok.Data;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Data
public class SupplierContractDto {

    private Long id;

    private String organization;

    private String value;

    private String year;

    private String goodService;

    private String destination;
}
