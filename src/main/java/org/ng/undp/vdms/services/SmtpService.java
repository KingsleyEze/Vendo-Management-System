package org.ng.undp.vdms.services;

import org.apache.commons.lang3.StringUtils;
import org.thymeleaf.context.Context;

import javax.transaction.Transactional;

import org.ng.undp.vdms.domains.constants.Env;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.concurrent.Future;

/**
 * Created by emmanuel on 2/10/17.
 */
@Service
@Transactional
public class SmtpService {

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(SmtpService.class);

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private TemplateEngine emailTemplateEngine;

    public  boolean sendSmtp(String email, String subject, String body, String sender, String sendName) {


        if(Env.PROD != userService.getEnvironmentProfile()){
            LOGGER.info("Message can only be sent in Production System");
           // return false;
        }

        if(StringUtils.isEmpty(email)){
            LOGGER.info("Failure: can not send to an empty email.");
            //return false;
        }

        if(StringUtils.isEmpty(sender)){
            sender = "no-reply@vdms.lepsms.com";
        }

        if(StringUtils.isEmpty(sendName)){
            sendName = "Vendor Database Management System";
        }

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(new InternetAddress(sender, sendName.trim()));
            helper.setTo(new InternetAddress(email));
            helper.setText(body, true);
            helper.setSubject(subject);
            mailSender.send(message);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            LOGGER.info(ex.getMessage());
            return false;
        }
        return true;
    }

    @Async
    public void sendSmtpAsync(String email, String subject, String body, String sender, String sendName) {
        System.out.println("trying to send from Async");
        sendSmtp(email, subject, body, sender, sendName);
    }

    public String prepareThymeleafMailBody(String emailTemplate, Context ctx){
        return this.emailTemplateEngine.process( emailTemplate, ctx);
    }

}

