package org.ng.undp.vdms.services;


import org.ng.undp.vdms.domains.Notice;

import org.ng.undp.vdms.domains.Vendor;

import org.ng.undp.vdms.domains.security.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import java.util.List;


/**
 * Created by macbook on 7/1/17.
 */

@Service
public class BackgroundServices {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private SmtpService smtpService;
    @Autowired
    private NoticeService noticeService;

    @Async
    public void newNoticeAlertBlaster(Role userType, Notice notice) {

        try {

            List<Vendor> vendorList = vendorService.findAll();

            for (Vendor v : vendorList) {

                if (v.getUser().getRoleNames().contains(userType.getName())) {
                    newNoticeAlertMail(v, notice);
                }


            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void newNoticeAlertMail(Vendor v, Notice notice) {

        final Context messageContext = new Context();


        messageContext.setVariable("userName", v.getUser().getLastname());

        String messageBody =
                smtpService.prepareThymeleafMailBody("new-notice-alert", messageContext);

        smtpService.sendSmtpAsync(v.getUser().getEmail(), "New Notice Alert from VDMS ", messageBody, "", "");
        notice.setAlertSent(true);
        noticeService.save(notice);
    }
}
