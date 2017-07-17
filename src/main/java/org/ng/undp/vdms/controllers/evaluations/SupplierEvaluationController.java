package org.ng.undp.vdms.controllers.evaluations;

import org.apache.commons.lang3.StringUtils;
import org.ng.undp.vdms.constants.RatingAttributes;
import org.ng.undp.vdms.constants.SupplierRatingAttributes;
import org.ng.undp.vdms.controllers.BaseController;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.Supplier;
import org.ng.undp.vdms.domains.SupplierCompany;
import org.ng.undp.vdms.domains.evaluations.*;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.domains.settings.Agency;
import org.ng.undp.vdms.domains.settings.Department;
import org.ng.undp.vdms.domains.settings.Station;
import org.ng.undp.vdms.services.ContractService;
import org.ng.undp.vdms.services.evaluations.ConsultantEvaluationService;
import org.ng.undp.vdms.services.evaluations.ConsultantLanguageService;
import org.ng.undp.vdms.services.evaluations.SupplierEvaluationService;
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

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.Country;
import org.ng.undp.vdms.domains.evaluations.SupplierEvaluation;


/**
 * Created by macbook on 7/3/17.
 */


@Controller
@RequestMapping("evaluations")
@PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
public class SupplierEvaluationController  extends BaseController{


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ContractService contractService;


    @Autowired
    private ConsultantEvaluationService consultantEvaluationService;


    @Autowired
    private SupplierEvaluationService supplierEvaluationService;



    @ModelAttribute
    public Model commonModels(Model model) {
        Map<Long, String> countries = new HashMap<Long, String>();
        Accessor.findList(Country.class, Filter.get()).forEach(p -> {
            countries.put(p.getId(), p.getName());
        });

        model.addAttribute("countries", countries);
        model.addAttribute("ratings", SupplierRatingAttributes.values());

        model.addAttribute("agencies", Accessor.findList(Agency.class, Filter.get()));
        model.addAttribute("stations", Accessor.findList(Station.class, Filter.get()));
        Role consultantRole = roleService.findByName("SUPPLIER");
        Set<Role> rolez = new HashSet<Role>(1);
        rolez.add(consultantRole);

        model.addAttribute("departments", Accessor.findList(Department.class, Filter.get()));
        model.addAttribute("contracts", Accessor.findList(Contract.class, Filter.get()));
        model.addAttribute("suppliers", Accessor.findList(SupplierCompany.class, Filter.get().field("user.deleted_at").isNull()));

        return model;
    }


    @GetMapping(value = "suppliers/ratingForm")
    public String addRating(Principal principal, Model model) {
        model.addAttribute("supplier", new SupplierEvaluation());

        return "supplierevaluation/evaluate";
    }



    @PostMapping(value = "suppliers/ratingForm")
    public String addRating(SupplierEvaluation consultantEvaluation, BindingResult bindingResult, HttpServletRequest request,
                            Principal principal, Model mod, RedirectAttributes model) throws Exception {


        if ( consultantEvaluation.getNatureOfServicesProvided() == null

                ) {

            errors.add("Nature of service provided cannot be null ");
            mod.addAttribute("errors", Utility.errors(errors));
            mod.addAttribute("supplier", consultantEvaluation);

            return "supplierevaluation/evaluate";
        }


        if ( consultantEvaluation.getStartDate() == null || consultantEvaluation.getEndDate() == null

                ) {

            errors.add("Start and End date cannot be empty ");
            mod.addAttribute("errors", Utility.errors(errors));
            mod.addAttribute("supplier", consultantEvaluation);

            return "supplierevaluation/evaluate";
        }

        if (supplierEvaluationService.findOneByContract(consultantEvaluation.getContract()) != null

                ) {

            errors.add("Duplicate contract detected");
            mod.addAttribute("errors", Utility.errors(errors));
            mod.addAttribute("supplier", consultantEvaluation);

            return "supplierevaluation/evaluate";
        }
        if ( supplierEvaluationService.findOneByContractNumber(consultantEvaluation.getContractNumber()) != null) {

            errors.add("Duplicate  Contract number detected");
            mod.addAttribute("errors", Utility.errors(errors));
            mod.addAttribute("supplier", consultantEvaluation);

            return "supplierevaluation/evaluate";
        }

        if (DateUtils.isFirstDateBeforeSecondDate(consultantEvaluation.getStartDate(), consultantEvaluation.getEndDate()) != true) {

            errors.add("Project End date cannot be before the start date, Please correct the error!");
            mod.addAttribute("errors", Utility.errors(errors));
            mod.addAttribute("supplier", consultantEvaluation);

            return "supplierevaluation/evaluate";
        }


        try {
            if (supplierEvaluationService.save(consultantEvaluation) != null) {


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



