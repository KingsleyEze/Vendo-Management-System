package org.ng.undp.vdms.controllers;

import groovy.util.logging.Commons;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.dao.Pager;
import org.ng.undp.vdms.dao.Param;
import org.ng.undp.vdms.domains.Notice;
import org.ng.undp.vdms.domains.Tender;
import org.ng.undp.vdms.domains.User;
import org.ng.undp.vdms.domains.Vendor;
import org.ng.undp.vdms.domains.constants.TenderStatus;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.dto.CustomUserDetails;
import org.ng.undp.vdms.services.NoticeService;
import org.ng.undp.vdms.services.TenderService;
import org.ng.undp.vdms.services.VendorProfileStatusService;
import org.ng.undp.vdms.services.VendorService;
import org.ng.undp.vdms.utils.ShortUUID;
import org.ng.undp.vdms.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;

/**
 * Created by macbook on 6/16/17.
 */

@Controller
@RequestMapping(value = "tenders")
public class TenderController extends BaseController {
    @Autowired
    private TenderService tenderService;

    @Autowired
    private VendorService vendorService;

    @Autowired
    NoticeService noticeService;
    @Autowired
    private VendorProfileStatusService vendorProfileStatusService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping(value = "apply/{noticeUUID}")
    public String apply(@PathVariable String noticeUUID, Principal principal, RedirectAttributes model) {
        String vendorUUID = vendorService.findOneByUser(((CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser()).getUuid();
        logger.info("VENDOR UUID " + vendorUUID);

        boolean vendorStatus = vendorProfileStatusService.checkVendorProfileStatus(vendorUUID);

        Notice notice = noticeService.findOneByUuid(noticeUUID);
        Vendor vendor = vendorService.findOneByUuid(vendorUUID);


        logger.info("VENDOR STATUS " + vendorStatus);
        logger.info("VENDOR UUID " + vendor.getUuid());
        logger.info("NOTICE UPLOAD? " + notice.isRequireUploadByVendor());
        logger.info("NOTICE UUID? " + noticeUUID);

        if ((isVendorRoleAllowed(vendorUUID, noticeUUID) != true)) {
            errors.add("This Notice is not open to your vendor category!");

            model.addFlashAttribute("errors", Utility.errors(errors));
            return "redirect:/accounts";


        }


        if (hasVendorAppliedBefore(vendorUUID, noticeUUID) == false &&
                (vendorStatus == true) && vendor != null && notice != null &&
                (notice.isRequireUploadByVendor() == false)) {

            logger.info("Inside tender apply if block");

            Tender tender = new Tender();
            tender.setVendor(vendor);

            tender.setNotice(notice);
            tender.setUuid(ShortUUID.shortUUID().toString());
            Tender tender1 = tenderService.save(tender);
            List<Tender> tenders = new ArrayList<>(1);
            tenders.add(tender1);
            notice.setTenders(tenders);
            noticeService.save(notice);


            if (null != tender1) {
                success.add("Your tender has been received");

                model.addFlashAttribute("success", Utility.success(success));

                return "redirect:/accounts";

            } else {
                errors.add("Your tender was rejected");

                model.addFlashAttribute("errors", Utility.errors(errors));
                return "redirect:/accounts";


            }


        } else {

            errors.add("Your tender has been received earlier!");

            model.addFlashAttribute("errors", Utility.errors(errors));
            return "redirect:/accounts";

        }

    }

    @GetMapping(value = "me")
    public String apply(Principal principal, RedirectAttributes mod, Model model) {

        Vendor vendor = vendorService.findOneByUser(this.loggedInUser(principal).getUser());
        String vendorUUID = vendor.getUuid();
        logger.info("VENDOR  " + vendor.getUuid());


        boolean vendorStatus = vendorProfileStatusService.checkVendorProfileStatus(vendorUUID);

        if (vendorStatus == false) {
            errors.add("Please complete your profile to access that area");
            mod.addFlashAttribute("errors", errors);
            return this.redirectVendor(this.loggedInUser(principal));
        }


        List<Tender> tenders = Accessor.findList(Tender.class, Filter.get().field("vendor", vendor).field("tenderStatus").ne(TenderStatus.WITHDRAWN_BY_BIDDER));

        if (tenders.size() != 0) {
            model.addAttribute("tenders", tenders);
            return "tenders/mytenders";
        } else {
            success.add("You have not made any tender(s)");
            mod.addFlashAttribute("success", Utility.success(success));
            return "redirect:/accounts";

        }


    }

    @GetMapping(value = "withdrawBid/{tenderId}")
    public String withdrawBidGet(@PathVariable Long tenderId, Principal principal, Model model, RedirectAttributes red) {

        Tender tender = Accessor.findOne(Tender.class, Filter.get().field("id", tenderId));

        Vendor vendor = vendorService.findOneByUser(this.loggedInUser(principal).getUser());

        //Check if loggedin vendor is the owner of the tender
        if (tender.getVendor().getUser().getId().equals(vendor.getUser().getId())) {

            String vendorUUID = vendor.getUuid();
            logger.info("VENDOR  " + vendor.getUuid());
            model.addAttribute("faq", tender);
            model.addAttribute("vendor", vendor);

            return "tenders/withdrawbid";
        } else {
            errors.add("Security breach attempt detected. you have been redirected...");
            red.addFlashAttribute("errors", Utility.errors(errors));
            return "redirect:/accounts";
        }


    }

    @PostMapping(value = "withdrawBid/{tenderId}")
    public String withdrawBidPost(@PathVariable Long tenderId, Principal principal, RedirectAttributes mod, Model model, RedirectAttributes red) {


        Tender tender = Accessor.findOne(Tender.class, Filter.get().field("id", tenderId));

        Vendor vendor = vendorService.findOneByUser(this.loggedInUser(principal).getUser());

        //Check if loggedin vendor is the owner of the tender
        if (tender.getVendor().getUser().getId().equals(vendor.getUser().getId())) {

            String vendorUUID = vendor.getUuid();
            logger.info("VENDOR  " + vendor.getUuid());
            tender.setTenderStatus(TenderStatus.WITHDRAWN_BY_BIDDER);
            tenderService.save(tender);


            success.add("Bid successfully withdrawn!");
            red.addFlashAttribute("success", Utility.success(success));

        }
        return "redirect:/accounts";

    }

    @GetMapping
    public String index(HttpServletRequest request, Model model) {
        Param p = Utility.getParam(request);
        String noticeUUID = request.getParameter("noticeUUID");
        if (noticeUUID != null) {
            model.addAttribute("notice", Accessor.findOne(Notice.class, Filter.get().field("uuid", noticeUUID)));
            model.addAttribute("tenders", Accessor.findList(Tender.class, Filter.get().field("notice.uuid", noticeUUID), p));

        } else {
            model.addAttribute("tenders", Accessor.findList(Tender.class, Filter.get(), p));
        }

        return "tenders/index";
    }

    private boolean hasVendorAppliedBefore(String vendorUUID, String noticeUUID) {

        Long appliedCount = Accessor.count(Tender.class, Filter.get().field("notice.uuid", noticeUUID).field("vendor.uuid", vendorUUID));
        if (appliedCount > 0) {
            return true;
        }
        return false;

    }

    private boolean isVendorRoleAllowed(String vendorUUID, String noticeUUID) {
        Vendor vendor = vendorService.findOneByUuid(vendorUUID);
        Set<String> vRoles = vendor.getUser().getRoleNames();

        vRoles.remove("VENDOR");
        String vendorRole = getApplyingVendorRole("VENDOR", vRoles);


        logger.info("vROles size :" + Integer.toString(vRoles.size()));
        Notice appliedCount = noticeService.findOneByUuid(noticeUUID);


        boolean allowed = appliedCount.getRoleNames().contains(vendorRole);

        logger.info(Boolean.toString(allowed));
        return allowed;

    }


    String getApplyingVendorRole(String sample, Set<String> all) {
        for (String one : all) {
            if (!one.equals(sample)) {
                return one;
            }
        }
        return null;
    }

    private boolean isVendorVPAAllowed(String vendorUuid, String noticeUuid) {
        Vendor vendor = vendorService.findOneByUuid(vendorUuid);

        Long appliedCount = Accessor.count(Notice.class, Filter.get().field("uuid", noticeUuid).field("roles").contains(vendor.getUser().getRoles()));
        if (appliedCount > 0) {
            return true;
        }
        return false;

    }

    private boolean isVendorVSSAllowed(String vendorUUID, String noticeUUID) {
        Vendor vendor = vendorService.findOneByUuid(vendorUUID);

        Long appliedCount = Accessor.count(Notice.class, Filter.get().field("uuid", noticeUUID).field("roles").contains(vendor.getUser().getRoles()));
        if (appliedCount > 0) {
            return true;
        }
        return false;

    }

}
