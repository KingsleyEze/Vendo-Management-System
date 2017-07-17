package org.ng.undp.vdms.viewResolvers;

/**
 * Created by macbook on 7/7/17.
 */


import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.ng.undp.vdms.views.*;
import java.util.Locale;

public class PdfViewResolver implements ViewResolver {
    @Override
    public View resolveViewName(String s, Locale locale) throws Exception {
        PdfViewUsers view = new PdfViewUsers();
        return view;
    }
}