package org.ng.undp.vdms.dto;

import lombok.Data;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 7/6/2017.
 */
@Data
public class NgoProfileDto {


    private NgoCompanyDto companyDto;

    private NgoFinancialDto financialDto;

    private NgoTechnicalDto technicalDto;

    private NgoExperienceDto experienceDto;

    private NgoOtherDto otherDto;

    public NgoProfileDto(){
        this.companyDto = new NgoCompanyDto();
        this.financialDto = new NgoFinancialDto();
        this.technicalDto = new NgoTechnicalDto();
        this.experienceDto = new NgoExperienceDto();
        this.otherDto = new NgoOtherDto();
    }

    @Override
    public String toString() {
        return "NgoProfileDto{}";
    }
}
