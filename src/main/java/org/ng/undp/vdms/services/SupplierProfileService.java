package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 7/6/2017.
 */

@Service
public class SupplierProfileService {

    @Autowired
    private SupplierCompanyService companyService;
    @Autowired
    private SupplierFinancialService financialService;
    @Autowired
    private SupplierTechnicalService technicalService;
    @Autowired
    private SupplierExperienceService experienceService;
    @Autowired
    private SupplierOtherService otherService;


    public SupplierProfileDto populateSupplierDto(User user){

        SupplierProfileDto dto = new SupplierProfileDto();

        SupplierCompanyDto companyDto =
                companyService.populateCompany(user);

        if(Objects.isNull(companyDto)) companyDto = new SupplierCompanyDto();

        SupplierFinancialDto financialDto =
                financialService.populateFinancial(user);

        if(Objects.isNull(financialDto)) financialDto =  new SupplierFinancialDto();

        SupplierTechnicalDto technicalDto =
                technicalService.populateTechnical(user);

        if(Objects.isNull(technicalDto)) technicalDto = new SupplierTechnicalDto();

        SupplierExperienceDto experienceDto =
                experienceService.populateExperience(user);

        if(Objects.isNull(experienceDto)) experienceDto = new SupplierExperienceDto();


        SupplierOtherDto otherDto =
                otherService.populateOther(user);

        if(Objects.isNull(otherDto)) otherDto = new SupplierOtherDto();

        dto.setCompanyDto(companyDto);
        dto.setFinancialDto(financialDto);
        dto.setTechnicalDto(technicalDto);
        dto.setExperienceDto(experienceDto);
        dto.setOtherDto(otherDto);



        return dto;
    }
}
