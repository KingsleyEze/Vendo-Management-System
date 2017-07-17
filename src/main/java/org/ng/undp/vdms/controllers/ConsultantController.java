package org.ng.undp.vdms.controllers;

import org.apache.commons.lang3.StringUtils;
import org.ng.undp.vdms.constants.MaritalStatus;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.Consultant;
import org.ng.undp.vdms.domains.Country;
import org.ng.undp.vdms.domains.constants.InstitutionType;
import org.ng.undp.vdms.domains.constants.Sex;
import org.ng.undp.vdms.domains.consultants.*;
import org.ng.undp.vdms.repositories.*;
import org.ng.undp.vdms.repositories.security.LanguageSkillRepository;
import org.ng.undp.vdms.services.ConsultantService;
import org.ng.undp.vdms.utils.Auth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by abdulhakim on 11/12/16.
 */
@Controller
@RequestMapping(value = "consultants")
public class ConsultantController extends BaseController {

    @Autowired
    private BioDataRepository bioDataRepository;

    @Autowired
    private EduDataRepository eduDataRepository;

    @Autowired
    private EmploymentDataRepository employmentDataRepository;

    @Autowired
    private LanguageSkillRepository languageSkillRepository;

    @Autowired
    private InstitutionAttendedRepository institutionAttendedRepository;

    @Autowired
    private EmploymentRecordRepository employmentRecordRepository;

    @Autowired
    private ConsultantRepository consultantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @ModelAttribute
    public Model commonModels(Model model) {
        List<String> countries = new ArrayList<>();
        Accessor.findList(Country.class, Filter.get()).forEach(p -> {
            countries.add(p.getName());
        });

        model.addAttribute("countries", countries);
        model.addAttribute("maritalStatus", Arrays.asList(MaritalStatus.values()));
        model.addAttribute("genders", Arrays.asList(Sex.values()));
        return model;
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','CONSULTANT','STAFF','UNSTAFF')")
    public String all(Model model, HttpServletRequest request) {

        if (StringUtils.isEmpty(request.getQueryString())) {
            model.addAttribute("consultants", Accessor.findList(Consultant.class, Filter.get()));
            return "consultants/all";
        }


        Filter f = buildQueryForConsultant(request);
        model.addAttribute("consultants", Accessor.findList(Consultant.class, f));

        return "consultants/all";

    }

    @GetMapping(value = "/page/{consultantId}")
    public String viewConsultant(@PathVariable Long consultantId, Model model, HttpServletResponse response) {

        Consultant consultant = Accessor.findOne(Consultant.class, consultantId);

        if (consultant == null) {
            //throw page not found exception
            try {
                response.sendError(404);
            } catch (IOException ioe) {
                System.out.println("An exception happened!");
            }

            return "redirect:/";
        }

        model.addAttribute("consultant", Accessor.findOne(Consultant.class, consultantId));
        model.addAttribute("languageSkills", Accessor.findList(LanguageSkill.class, Filter.get().field("bioData.id", consultant.getBioData().getId())));


        if (null != consultant.getEducationalData()) {
            model.addAttribute("eduData", consultant.getEducationalData());
            model.addAttribute("institutionsAttended", Accessor.findList(InstitutionAttended.class, Filter.get().field("educationalData.id", consultant.getEducationalData().getId())));
        } else {
            model.addAttribute("noEduData", true);
        }

        if (null != consultant.getEmploymentData()) {
            model.addAttribute("empData", consultant.getEmploymentData());
            model.addAttribute("employmentRecords", Accessor.findList(EmploymentRecord.class, Filter.get().field("employmentData.id", consultant.getEmploymentData().getId())));
        } else {
            model.addAttribute("noEmpData", true);
        }

        return "consultants/page";
    }

    @PreAuthorize("hasAuthority('CONSULTANT')")
   // @PreAuthorize("isVendor('CONSULTANT')")

    @GetMapping(value = "addBioData")
    public String addBioData(Model model) {
        model.addAttribute("bioData", new BioData());
        model.addAttribute("consultant", new Consultant());


        return "consultants/add-bio-data";
    }

    @PreAuthorize("hasAuthority('CONSULTANT')")
    @GetMapping(value = "addEduData/{consultantId}")
    public String addEduData(@PathVariable Long consultantId, Model model) {

        model.addAttribute("eduData", new EducationalData());
        model.addAttribute("consultant", Accessor.findOne(Consultant.class, consultantId));
        model.addAttribute("institutionTypes", Arrays.asList(InstitutionType.values()));

        return "consultants/add-edu-data";
    }

    @PreAuthorize("hasAuthority('CONSULTANT')")
    @GetMapping(value = "addEmpData/{consultantId}")
    public String addEmpData(@PathVariable Long consultantId, Model model) {

        model.addAttribute("empData", new EmploymentData());
        model.addAttribute("consultant", Accessor.findOne(Consultant.class, consultantId));


        return "consultants/add-emp-data";
    }

    @PreAuthorize("hasAuthority('CONSULTANT')")
    @PostMapping(value = "addBioData")
    public String postBioData(@ModelAttribute("biodata") BioData bioData, Model model, HttpServletRequest request) {

        List<LanguageSkill> languageSkills = bioData.getLanguageSkills();

        System.out.println("Language skills: " + languageSkills);

        addressRepository.save(bioData.getOfficeAddress());
        addressRepository.save(bioData.getPermanentAddress());
        addressRepository.save(bioData.getPresentAddress());

        bioDataRepository.save(bioData);

        Consultant consultant = Accessor.findOne(Consultant.class, Filter.get().field("bioData.id", bioData.getId()));

        if (null == consultant) {//New entrance
            consultant = new Consultant();
            consultant.setUser(Auth.INSTANCE.getAuth().get());
            consultant.setBioData(bioData);
            consultantRepository.save(consultant);
        }

        for (LanguageSkill l : languageSkills) {
            l.setBioData(bioData);
            languageSkillRepository.save(l);
        }

        return "redirect:/consultants/addEduData/" + consultant.getId();
    }

    @PreAuthorize("hasAuthority('CONSULTANT')")
    @PostMapping(value = "addEduData")
    public String postEduData(@ModelAttribute("edudata") EducationalData eduData, Model model, HttpServletRequest request) {

        eduDataRepository.save(eduData);

        Consultant consultant = Accessor.findOne(Consultant.class, eduData.getConsultantId());
        consultant.setEducationalData(eduData);
        consultantRepository.save(consultant);

        for (InstitutionAttended i : eduData.getInstitutionsAttended()) {
            i.setEducationalData(eduData);
            institutionAttendedRepository.save(i);
        }

        return "redirect:/consultants/addEmpData/" + consultant.getId();

    }

    @PreAuthorize("hasAuthority('CONSULTANT')")

    @PostMapping(value = "addEmpData")
    public String postEmpData(@ModelAttribute("empdata") EmploymentData empData, Model model, HttpServletRequest request) {

        employmentDataRepository.save(empData);

        Consultant consultant = Accessor.findOne(Consultant.class, empData.getConsultantId());
        consultant.setEmploymentData(empData);
        consultantRepository.save(consultant);

        for (EmploymentRecord e : empData.getEmploymentRecords()) {
            e.setEmploymentData(empData);
            employmentRecordRepository.save(e);
        }

        return "redirect:/consultants/page/" + consultant.getId();

    }

    @PreAuthorize("hasAuthority('CONSULTANT')")
    @GetMapping(value = "editBioData/{consultantId}")
    public String editBioData(@PathVariable Long consultantId, Model model) {
        Consultant consultant = Accessor.findOne(Consultant.class, consultantId);

        if (consultant == null) {
            return "redirect:/consultants/addBioData";
        }

        BioData bioData = consultant.getBioData();
        if (bioData == null) {
            return "redirect:/consultants/addBioData";
        }

        bioData.setLanguageSkills(bioData.getLanguageSkillsFromDb());
        model.addAttribute("bioData", bioData);
        model.addAttribute("consultant", new Consultant());
        model.addAttribute("edit", "edit");


        return "consultants/add-bio-data";
    }

    @PreAuthorize("hasAuthority('CONSULTANT')")
    @GetMapping(value = "editEduData/{consultantId}")
    public String editEduData(@PathVariable Long consultantId, Model model) {
        Consultant consultant = Accessor.findOne(Consultant.class, consultantId);
        if (consultant == null) {
            return "redirect:/consultants/addBioData";
        }

        EducationalData eduData = consultant.getEducationalData();

        if (eduData == null) {
            return "redirect:/consultants/addEduData";
        }

        eduData.setInstitutionsAttended(eduData.getInstitutionsAttendedFromDb());
        model.addAttribute("eduData", eduData);
        model.addAttribute("consultant", consultant);
        model.addAttribute("institutionTypes", Arrays.asList(InstitutionType.values()));
        model.addAttribute("edit", "edit");

        return "consultants/add-edu-data";
    }

    @PreAuthorize("hasAuthority('CONSULTANT')")
    @GetMapping(value = "editEmpData/{consultantId}")
    public String editEmpData(@PathVariable Long consultantId, Model model) {

        Consultant consultant = Accessor.findOne(Consultant.class, consultantId);
        if (consultant == null) {
            return "redirect:/consultants/addBioData";
        }

        EmploymentData empData = consultant.getEmploymentData();

        if (empData == null) {
            return "redirect:/consultants/addEmpData";
        }

        empData.setEmploymentRecords(empData.getEmploymentRecordsFromDb());
        model.addAttribute("empData", empData);
        model.addAttribute("consultant", consultant);
        model.addAttribute("edit", "edit");


        return "consultants/add-emp-data";
    }


    private Filter buildQueryForConsultant(HttpServletRequest request) {

        Filter f = Filter.get();

        if (StringUtils.isNotBlank(request.getParameter("personalInfo"))) {
            String pInfo = request.getParameter("personalInfo");
            f.brS().field("bioData.firstName").like(pInfo)
                    .or().field("bioData.middleName").like(pInfo)
                    .or().field("bioData.surName").like(pInfo)
                    .or().field("bioData.maidenName").like(pInfo)
                    .brE();
        }

        if (StringUtils.isNotBlank(request.getParameter("nationality"))) {
            String nationality = request.getParameter("nationality");
            f.brS().field("bioData.presentNationalities").contains(nationality)
                    .or().field("bioData.nationalitiesAtBirth").contains(nationality)
                    .brE();
        }

        if (StringUtils.isNotBlank(request.getParameter("placeOfBirth"))) {
            f.field("bioData.placeOfBirth", request.getParameter("placeOfBirth"));
        }

        if (StringUtils.isNotBlank(request.getParameter("placeOfBirth"))) {
            f.field("bioData.motherTongue", request.getParameter("motherTongue"));
        }

        return f;
    }

}


