package org.ng.undp.vdms.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Created by abdulhakim on 11/10/16.
 */
@Service
public class MailContentBuilder {

    private TemplateEngine templateEngine;

    @Autowired
    public MailContentBuilder(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String build(String activationUrl, String name) {
        Context context = new Context();
        context.setVariable("name", name);
        context.setVariable("activationURL", activationUrl);
        return templateEngine.process("emailTemplates/mailTemplate", context);
    }

}