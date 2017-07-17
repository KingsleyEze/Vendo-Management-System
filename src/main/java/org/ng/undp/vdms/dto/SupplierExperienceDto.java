package org.ng.undp.vdms.dto;

import lombok.Data;
import org.ng.undp.vdms.domains.SupplierContract;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Data
public class SupplierExperienceDto {

    private Long id;

    private String supplierContractArray;

    private String supplierProject1;

    private String supplierCountry1;

    private String supplierProject2;

    private String supplierCountry2;

    private String supplierProject3;

    private String supplierCountry3;

    private MultipartFile supplierDocument;

    private List<SupplierContract> supplierContracts;

    private boolean isEDIEnabled;
}
