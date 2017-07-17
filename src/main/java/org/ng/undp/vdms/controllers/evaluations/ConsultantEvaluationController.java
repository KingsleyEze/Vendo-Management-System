package org.ng.undp.vdms.controllers.evaluations;

import org.ng.undp.vdms.constants.RatingAttributes;
import org.ng.undp.vdms.controllers.BaseController;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.Consultant;
import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.Country;
import org.ng.undp.vdms.domains.evaluations.ConsultantEvaluation;
import org.ng.undp.vdms.domains.evaluations.ConsultantLanguage;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.domains.settings.Agency;
import org.ng.undp.vdms.domains.settings.Department;
import org.ng.undp.vdms.domains.settings.Station;
import org.ng.undp.vdms.services.ContractService;
import org.ng.undp.vdms.services.evaluations.ConsultantEvaluationService;
import org.ng.undp.vdms.services.evaluations.ConsultantLanguageService;
import org.ng.undp.vdms.utils.DateUtils;
import org.ng.undp.vdms.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

/**
 * Created by macbook on 6/30/17.
 */

@Controller
@RequestMapping("evaluations")
@PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
public class ConsultantEvaluationController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractService contractService;


    @Autowired
    private ConsultantEvaluationService consultantEvaluationService;

    @Autowired
    private ConsultantLanguageService consultantLanguageService;

    @ModelAttribute
    public Model commonModels(Model model) {
        Map<Long, String> countries = new HashMap<Long, String>();
        Accessor.findList(Country.class, Filter.get()).forEach(p -> {
            countries.put(p.getId(), p.getName());
        });

        model.addAttribute("countries", countries);
        model.addAttribute("ratings", RatingAttributes.values());

        model.addAttribute("agencies", Accessor.findList(Agency.class, Filter.get()));
        model.addAttribute("stations", Accessor.findList(Station.class, Filter.get()));
        Role consultantRole = roleService.findByName("CONSULTANT");
        Set<Role> rolez = new HashSet<Role>(1);
        rolez.add(consultantRole);

        model.addAttribute("departments", Accessor.findList(Department.class, Filter.get()));
        model.addAttribute("contracts", Accessor.findList(Contract.class, Filter.get()));
        model.addAttribute("consultants", Accessor.findList(Consultant.class, Filter.get().field("user.deleted_at").isNull()));

        return model;
    }


    @GetMapping(value = "consultants/ratingForm")
    public String addRating(Principal principal, Model model) {
        model.addAttribute("consultant", new ConsultantEvaluation());

        return "icevaluation/evaluate";
    }

    @PostMapping(value = "consultants/ratingForm")
    public String addRating(ConsultantEvaluation consultantEvaluation, BindingResult bindingResult, HttpServletRequest request,
                            Principal principal,Model mod, RedirectAttributes model) throws Exception {
        List<ConsultantLanguage> langs = consultantEvaluation.getLanguages();
        if (consultantEvaluationService.findOneByContract(consultantEvaluation.getContract()) != null

                ) {

            errors.add("Duplicate contract detected");
            mod.addAttribute("errors", Utility.errors(errors));
            mod.addAttribute("consultant", consultantEvaluation);

            return "icevaluation/evaluate";
        }
        if ( consultantEvaluationService.findOneByProjectNumber(consultantEvaluation.getProjectNumber()) != null) {

            errors.add("Duplicate  Project number detected");
            mod.addAttribute("errors", Utility.errors(errors));
            mod.addAttribute("consultant", consultantEvaluation);

            return "icevaluation/evaluate";
        }

        if (DateUtils.isFirstDateBeforeSecondDate(consultantEvaluation.getStartDate(), consultantEvaluation.getEndDate()) != true) {

            errors.add("Project End date cannot be before the start date, Please correct the error!");
            mod.addAttribute("errors", Utility.errors(errors));
            mod.addAttribute("consultant", consultantEvaluation);

            return "icevaluation/evaluate";
        }


        try {
            if (consultantEvaluationService.save(consultantEvaluation) != null) {
                if (langs != null) {
                    for (ConsultantLanguage l : langs) {
                        logger.info(l.getLanguage() + l.getWritten() + l.getSpoken());
                        if (!StringUtils.isBlank(l.getLanguage().trim())) {
                            l.setConsultantEvaluation(consultantEvaluation);
                            consultantLanguageService.save(l);
                        }

                    }

                }
                String successMessage = "Successfully added evaluation!";
                success.add(successMessage);
                model.addFlashAttribute("success", Utility.success(success));

            }
            return "redirect:/accounts";
        } catch (Exception e) {
            throw (e);
        }

    }
}
