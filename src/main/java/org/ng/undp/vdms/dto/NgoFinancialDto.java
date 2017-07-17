package org.ng.undp.vdms.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Data
public class NgoFinancialDto {

    private Long id;

    private String bankName;

    private String bankAccountName;

    private String bankAccountNumber;

    private String bankAddress;

    private String swiftOrBicAddress;

    private String totalIncomeYearOne;

    private String totalIncomeYearTwo;

    private String totalIncomeYearThree;

    private String exportSalesYearOne;

    private String exportSalesYearTwo;

    private String exportSalesYearThree;

    private MultipartFile ngoDocument;
}
