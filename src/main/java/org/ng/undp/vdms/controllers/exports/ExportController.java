package org.ng.undp.vdms.controllers.exports;

import org.ng.undp.vdms.controllers.BaseController;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.Vendor;
import org.ng.undp.vdms.services.UserService;
import org.ng.undp.vdms.views.DynamicExcelView;
import org.ng.undp.vdms.views.ExcelViewUsers;
import org.ng.undp.vdms.views.ExcelViewVendors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.*;

/**
 * Created by macbook on 7/7/17.
 */

@Controller
@RequestMapping(value = "exports")
public class ExportController extends BaseController{

    @Autowired
    UserService userService;

    /**
     * Handle request to download an Excel document
     */
    @RequestMapping(value = "users", method = RequestMethod.GET)
    public ExcelViewUsers download(Model model) {
        model.addAttribute("users", Accessor.findList(User.class, Filter.get()));
        return new ExcelViewUsers();
    }

    @RequestMapping(value = "vendors", method = RequestMethod.GET)
    public ExcelViewVendors downloadVendors(Model model) {
        model.addAttribute("vendors", Accessor.findList(Vendor.class, Filter.get()));
        return new ExcelViewVendors();
    }

    @RequestMapping(value="myexcel/{entityName}/{fileName}", method=RequestMethod.GET)
    public ModelAndView getMyData(@PathVariable String entityName, @PathVariable String fileName, HttpServletRequest request, HttpServletResponse response) throws SQLException {
        Map<String, Object> model = new HashMap<String, Object>();
        //Sheet Name
        Object obj=null;
        model.put("sheetname",entityName );
        //Headers List
        if (entityName.equals("User")){


                model.put("headers", Arrays.asList(User.class.getFields()) );
                model.put("results",Accessor.findList(User.class, Filter.get()));
                model.put("entityName",entityName);
        }






        response.setContentType( "application/ms-excel" );
        response.setHeader( "Content-disposition", "attachment; filename=" + fileName + ".xls" );
        return new ModelAndView(new DynamicExcelView(), model);
    }
}