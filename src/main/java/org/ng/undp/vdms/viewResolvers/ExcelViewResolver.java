package org.ng.undp.vdms.viewResolvers;

import org.ng.undp.vdms.views.*;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;
import org.springframework.web.servlet.View;


/**
 * Created by macbook on 7/7/17.
 */

public class ExcelViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        ExcelViewUsers view = new ExcelViewUsers();
        return view;
    }
}