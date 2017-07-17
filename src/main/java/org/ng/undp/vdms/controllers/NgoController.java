package org.ng.undp.vdms.controllers;

import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.dto.*;
import org.ng.undp.vdms.services.*;
import org.ng.undp.vdms.utils.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

/**
 * Created by abdulhakim on 11/12/16.
 */

@Controller
@RequestMapping(value = "ngos")
public class NgoController extends  BaseController {



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
    @Autowired
    private NgoProfileService profileService;


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addForm(Model model) {
        return "ngos/add";
    }

    //======= Step 1
    @GetMapping(value = "/step-one")
    public ModelAndView stepOneView(){

        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        NgoCompanyDto companyDto =
                companyService.populateCompany(AUTH_USER);

        if(Objects.isNull(companyDto)) companyDto = new NgoCompanyDto();

        ModelAndView mav =
                new ModelAndView("ngos/step-one");
        mav.addObject("ngoCompanyDto", companyDto);
        return mav;
    }

    @PostMapping(value = "step-one")
    public ModelAndView stepOnePost(@ModelAttribute NgoCompanyDto ngoCompanyDto){

        ModelAndView mav =
                new ModelAndView("redirect:/ngos/step-two");

        companyService.createNgoCompany(ngoCompanyDto);
        return mav;
    }

    //======= Step 2
    @GetMapping(value = "/step-two")
    public ModelAndView stepTwoView(){

        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        NgoFinancialDto financialDto =
                financialService.populateFinancial(AUTH_USER);

        if(Objects.isNull(financialDto)) financialDto =  new NgoFinancialDto();

        ModelAndView mav =
                new ModelAndView("ngos/step-two");
        mav.addObject("ngoFinancialDto", financialDto);
        return mav;
    }

    @PostMapping(value = "/step-two")
    public ModelAndView stepTwoPost(@ModelAttribute NgoFinancialDto dto){

        ModelAndView mav =
                new ModelAndView("redirect:/ngos/step-three");

        financialService.createNgoFinancial(dto);

        return mav;
    }

    //======= Step 3
    @GetMapping(value = "/step-three")
    public ModelAndView stepThreeView(){

        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        NgoTechnicalDto technicalDto =
                technicalService.populateTechnical(AUTH_USER);

        if(Objects.isNull(technicalDto)) technicalDto = new NgoTechnicalDto();

        ModelAndView mav =
                new ModelAndView("ngos/step-three");
        mav.addObject("ngoTechnicalDto", technicalDto);
        return mav;
    }

    @PostMapping(value = "/step-three")
    public ModelAndView stepThreePost(@ModelAttribute NgoTechnicalDto dto){

        ModelAndView mav =
                new ModelAndView("redirect:/ngos/step-four");

        technicalService.createTechnical(dto);
        return mav;
    }

    //======= Step 4
    @GetMapping(value = "/step-four")
    public ModelAndView stepFourView(){
        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        NgoExperienceDto experienceDto =
                experienceService.populateExperience(AUTH_USER);

        if(Objects.isNull(experienceDto)) experienceDto = new NgoExperienceDto();

        ModelAndView mav =
                new ModelAndView("ngos/step-four");
        mav.addObject("ngoExperienceDto", experienceDto);
        return mav;
    }

    @PostMapping(value = "/step-four")
    public ModelAndView stepFourPost(@ModelAttribute NgoExperienceDto dto){


        ModelAndView mav =
                new ModelAndView("redirect:/ngos/step-five");

        experienceService.createNgoExperience(dto);

        return mav;
    }

    //======= Step 5
    @GetMapping(value = "/step-five")
    public ModelAndView stepFiveView(){

        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        NgoOtherDto otherDto =
                otherService.populateOther(AUTH_USER);

        if(Objects.isNull(otherDto)) otherDto = new NgoOtherDto();


        ModelAndView mav =
                new ModelAndView("ngos/step-five");
        mav.addObject("ngoOtherDto", otherDto);
        return mav;
    }

    @PostMapping(value = "/step-five")
    public ModelAndView stepFivePost(@ModelAttribute NgoOtherDto dto){

        ModelAndView mav =
                new ModelAndView("redirect:/ngos/confirm");

        otherService.createNgoOther(dto);

        return mav;
    }

    //======= Confirm
    @GetMapping(value = "/confirm")
    public ModelAndView confirmView(){


        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        NgoProfileDto profileDto =
                profileService.populateNgoDto(AUTH_USER);

        if(Objects.isNull(profileDto)) profileDto = new NgoProfileDto();
        ModelAndView mav =
                new ModelAndView("ngos/confirm");


        mav.addObject("profileDto", profileDto);

        return mav;
    }


    @PostMapping(value = "/confirm")
    public ModelAndView confirmPost(){


        ModelAndView mav =
                new ModelAndView("redirect:/ngos");
        return mav;
    }


    //======= PREVIEW
    @GetMapping(value = "/profile/{id}")
    public ModelAndView profileView(@PathVariable Long id){


        User AUTH_USER = userService.getUserById(id);

        NgoProfileDto profileDto =
                profileService.populateNgoDto(AUTH_USER);

        if(Objects.isNull(profileDto)) profileDto = new NgoProfileDto();
        ModelAndView mav =
                new ModelAndView("ngos/confirm");


        mav.addObject("profileDto", profileDto);

        return mav;
    }
}
