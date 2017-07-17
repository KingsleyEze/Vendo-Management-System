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
public class NgoProfileService {

    @Autowired
    private NgoCompanyService companyService;
    @Autowired
    private NgoFinancialService financialService;
    @Autowired
    private NgoTechnicalService technicalService;
    @Autowired
    private NgoExperienceService experienceService;
    @Autowired
    private NgoOtherService otherService;


    public NgoProfileDto populateNgoDto(User user){

        NgoProfileDto dto = new NgoProfileDto();

        NgoCompanyDto companyDto =
                companyService.populateCompany(user);

        if(Objects.isNull(companyDto)) companyDto = new NgoCompanyDto();

        NgoFinancialDto financialDto =
                financialService.populateFinancial(user);

        if(Objects.isNull(financialDto)) financialDto =  new NgoFinancialDto();

        NgoTechnicalDto technicalDto =
                technicalService.populateTechnical(user);

        if(Objects.isNull(technicalDto)) technicalDto = new NgoTechnicalDto();

        NgoExperienceDto experienceDto =
                experienceService.populateExperience(user);

        if(Objects.isNull(experienceDto)) experienceDto = new NgoExperienceDto();


        NgoOtherDto otherDto =
                otherService.populateOther(user);

        if(Objects.isNull(otherDto)) otherDto = new NgoOtherDto();

        dto.setCompanyDto(companyDto);
        dto.setFinancialDto(financialDto);
        dto.setTechnicalDto(technicalDto);
        dto.setExperienceDto(experienceDto);
        dto.setOtherDto(otherDto);



        return dto;
    }
}
