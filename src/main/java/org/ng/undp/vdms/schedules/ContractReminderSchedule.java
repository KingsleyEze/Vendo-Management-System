package org.ng.undp.vdms.schedules;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.services.ContractService;
import org.ng.undp.vdms.services.SmtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;

import java.util.Date;
import java.util.Objects;

/**
 * Created by macbook on 6/30/17.
 */

@Component
public class ContractReminderSchedule {
    @Autowired
    ContractService contractService  ;
    @Autowired
    private SmtpService smtpService;


    private int REMAINING_DAYS = 1;

    private Date TODAY;

    public ContractReminderSchedule() {

        TODAY = new Date();
    }
    /**
     * Checks the days remaining before terminating user subscription.
     */
    @Scheduled(fixedDelay = 60000)
    public void subscriptionRemainingDaysChecker() {
/*
        try {

            Iterable<Contract> subscriptions = contractService.findAll();

            if (!Objects.isNull(subscriptions)) {

                for (Contract subscription : subscriptions) {

                    DateTime dateTimeToday = new DateTime(TODAY);
                    DateTime dateTimeToExpire =
                            new DateTime(subscription.getContractDate());


                    int SubscriptionDaysRemains =  Days.daysBetween(dateTimeToday, dateTimeToExpire).getDays();

                    if(SubscriptionDaysRemains <= REMAINING_DAYS && !subscription.isReminderSent()){

                        subscription.setReminderSent(true);
                        subscriptionService.save(subscription);
                        subscriptionNotificationMail(subscription.getUser(), SubscriptionDaysRemains);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
    }



    private void contractExpiryNotificationMail(User user, int remainingDays){

        final Context messageContext = new Context();
        messageContext.setVariable("userName",user.getLastname());
        messageContext.setVariable("remainingDays", remainingDays);
        String messageBody =
                smtpService.prepareThymeleafMailBody("contract-reminder", messageContext);

        smtpService.sendSmtpAsync(user.getEmail(), "Contract expires next " + remainingDays + " days", messageBody, "", "");

    }
}
