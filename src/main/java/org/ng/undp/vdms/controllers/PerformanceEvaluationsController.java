package org.ng.undp.vdms.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by abdulhakim on 7/10/17.
 * <p>
 * <p>
 * <p>
 * This is strictly a BI module. Mostly readonly and export to excel format
 */
@Controller
@PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
@RequestMapping(value = "evaluations")
public class PerformanceEvaluationsController extends BaseController {

    @GetMapping
    public String viewAll(Model model, RedirectAttributes redmod) {


        return "evaluations/all";

    }


}
