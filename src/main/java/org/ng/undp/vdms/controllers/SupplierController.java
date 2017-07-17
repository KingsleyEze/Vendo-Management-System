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
@RequestMapping(value = "suppliers")
public class SupplierController extends BaseController {


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
    @Autowired
    private SupplierProfileService profileService;
    @Autowired
    private UserService userService;


    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addForm(Model model) {
        return "suppliers/add";
    }

    //======= Step 1
    @GetMapping(value = "/step-one")
    public ModelAndView stepOneView(){

        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        SupplierCompanyDto companyDto =
                companyService.populateCompany(AUTH_USER);

        if(Objects.isNull(companyDto)) companyDto = new SupplierCompanyDto();

        ModelAndView mav =
                new ModelAndView("suppliers/step-one");
        mav.addObject("supplierCompanyDto", companyDto);
        return mav;
    }

    @PostMapping(value = "step-one")
    public ModelAndView stepOnePost(@ModelAttribute SupplierCompanyDto supplierCompanyDto){

        ModelAndView mav =
                new ModelAndView("redirect:/suppliers/step-two");

        companyService.createSupplierCompany(supplierCompanyDto);
        return mav;
    }

    //======= Step 2
    @GetMapping(value = "/step-two")
    public ModelAndView stepTwoView(){

        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        SupplierFinancialDto financialDto =
                financialService.populateFinancial(AUTH_USER);

        if(Objects.isNull(financialDto)) financialDto =  new SupplierFinancialDto();

        ModelAndView mav =
                new ModelAndView("suppliers/step-two");
        mav.addObject("supplierFinancialDto", financialDto);
        return mav;
    }

    @PostMapping(value = "/step-two")
    public ModelAndView stepTwoPost(@ModelAttribute SupplierFinancialDto dto){

        ModelAndView mav =
                new ModelAndView("redirect:/suppliers/step-three");

        financialService.createSupplierFinancial(dto);

        return mav;
    }

    //======= Step 3
    @GetMapping(value = "/step-three")
    public ModelAndView stepThreeView(){

        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        SupplierTechnicalDto technicalDto =
                technicalService.populateTechnical(AUTH_USER);

        if(Objects.isNull(technicalDto)) technicalDto = new SupplierTechnicalDto();

        ModelAndView mav =
                new ModelAndView("suppliers/step-three");
        mav.addObject("supplierTechnicalDto", technicalDto);
        return mav;
    }

    @PostMapping(value = "/step-three")
    public ModelAndView stepThreePost(@ModelAttribute SupplierTechnicalDto dto){

        ModelAndView mav =
                new ModelAndView("redirect:/suppliers/step-four");

        technicalService.createTechnical(dto);
        return mav;
    }

    //======= Step 4
    @GetMapping(value = "/step-four")
    public ModelAndView stepFourView(){
        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        SupplierExperienceDto experienceDto =
                experienceService.populateExperience(AUTH_USER);

        if(Objects.isNull(experienceDto)) experienceDto = new SupplierExperienceDto();

        ModelAndView mav =
                new ModelAndView("suppliers/step-four");
        mav.addObject("supplierExperienceDto", experienceDto);
        return mav;
    }

    @PostMapping(value = "/step-four")
    public ModelAndView stepFourPost(@ModelAttribute SupplierExperienceDto dto){


        ModelAndView mav =
                new ModelAndView("redirect:/suppliers/step-five");

        experienceService.createSupplierExperience(dto);

        return mav;
    }

    //======= Step 5
    @GetMapping(value = "/step-five")
    public ModelAndView stepFiveView(){

        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        SupplierOtherDto otherDto =
                otherService.populateOther(AUTH_USER);

        if(Objects.isNull(otherDto)) otherDto = new SupplierOtherDto();


        ModelAndView mav =
                new ModelAndView("suppliers/step-five");
        mav.addObject("supplierOtherDto", otherDto);
        return mav;
    }

    @PostMapping(value = "/step-five")
    public ModelAndView stepFivePost(@ModelAttribute SupplierOtherDto dto){

        ModelAndView mav =
                new ModelAndView("redirect:/suppliers/confirm");

        otherService.createSupplierOther(dto);

        return mav;
    }

    //======= Confirm
    @GetMapping(value = "/confirm")
    public ModelAndView confirmView(){


        User AUTH_USER = Auth.INSTANCE.getAuth().get();

        SupplierProfileDto profileDto =
                profileService.populateSupplierDto(AUTH_USER);

        if(Objects.isNull(profileDto)) profileDto = new SupplierProfileDto();
        ModelAndView mav =
                new ModelAndView("suppliers/confirm");


        mav.addObject("profileDto", profileDto);

        return mav;
    }


    @PostMapping(value = "/confirm")
    public ModelAndView confirmPost(){


        ModelAndView mav =
                new ModelAndView("redirect:/suppliers");
        return mav;
    }


    //======= PREVIEW
    @GetMapping(value = "/profile/{id}")
    public ModelAndView profileView(@PathVariable Long id){


        User AUTH_USER = userService.getUserById(id);

        SupplierProfileDto profileDto =
                profileService.populateSupplierDto(AUTH_USER);

        if(Objects.isNull(profileDto)) profileDto = new SupplierProfileDto();
        ModelAndView mav =
                new ModelAndView("suppliers/confirm");


        mav.addObject("profileDto", profileDto);

        return mav;
    }

}
