package org.ng.undp.vdms.dto;

import lombok.Data;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 7/6/2017.
 */
@Data
public class SupplierProfileDto {


    private SupplierCompanyDto companyDto;

    private SupplierFinancialDto financialDto;

    private SupplierTechnicalDto technicalDto;

    private SupplierExperienceDto experienceDto;

    private SupplierOtherDto otherDto;

    public SupplierProfileDto(){
        this.companyDto = new SupplierCompanyDto();
        this.financialDto = new SupplierFinancialDto();
        this.technicalDto = new SupplierTechnicalDto();
        this.experienceDto = new SupplierExperienceDto();
        this.otherDto = new SupplierOtherDto();
    }

    @Override
    public String toString() {
        return "SupplierProfileDto{}";
    }
}
