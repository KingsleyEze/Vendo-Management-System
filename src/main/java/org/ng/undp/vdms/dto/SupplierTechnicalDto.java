package org.ng.undp.vdms.dto;

import lombok.Data;
import org.ng.undp.vdms.domains.SupplierGood;
import org.ng.undp.vdms.domains.SupplierOffice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Data
public class SupplierTechnicalDto {

    private Long id;

    private String supplierCompanyArray;

    private String supplierGoodArray;

    private List<SupplierGood> supplierGood;

    private List<SupplierOffice> supplierOffices;

    private MultipartFile supplierDocument;

}
