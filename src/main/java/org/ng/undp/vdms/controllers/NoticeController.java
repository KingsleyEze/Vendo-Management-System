package org.ng.undp.vdms.controllers;

import com.amazonaws.services.s3.model.ObjectMetadata;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.dao.Pager;
import org.ng.undp.vdms.dao.Param;
import org.ng.undp.vdms.domains.*;
import org.ng.undp.vdms.domains.constants.ReviewStatus;
import org.ng.undp.vdms.domains.constants.Status;
import org.ng.undp.vdms.domains.constants.UserType;
import org.ng.undp.vdms.domains.settings.Agency;
import org.ng.undp.vdms.domains.settings.Department;
import org.ng.undp.vdms.domains.settings.Station;
import org.ng.undp.vdms.dto.DocumentUploadFormDto;
import org.ng.undp.vdms.services.AwsS3Service;
import org.ng.undp.vdms.services.NoticeService;
import org.ng.undp.vdms.services.VendorService;
import org.ng.undp.vdms.services.VpaService;
import org.ng.undp.vdms.storage.StorageService;
import org.ng.undp.vdms.utils.Auth;
import org.ng.undp.vdms.utils.Utility;
import org.ng.undp.vdms.utils.VdmsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * Created by macbook on 6/16/17.
 */
@RequestMapping(value = "notices")
@Controller
public class NoticeController extends BaseController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    NoticeService noticeService;

    @Autowired
    private VpaService vpaService;

    @Value("${aws_basepath}")
    private String awsBasePath;

    @Value("${aws_namecard_bucket}")
    private String nameCardBucket;

    @Autowired
    private StorageService storageService;


    @Autowired
    private AwsS3Service awsS3Service;
    @Autowired
    private MultipartResolver multipartResolver;

    @RequestMapping(value = "create", method = RequestMethod.GET)
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
    public String createNotice(HttpServletRequest request, Model model) {
        Param p = Utility.getParam(request);

        p.setSort("name");


        model.addAttribute("stations", Accessor.findList(Station.class, Filter.get(), p));
        model.addAttribute("departments", Accessor.findList(Department.class, Filter.get(), p));

        p.setSort("acronym");
        model.addAttribute("agencies", Accessor.findList(Agency.class, Filter.get(), p));

        model.addAttribute("notice", new Notice());
        model.addAttribute("vendors", VdmsUtils.getVendorRoles());
        return "notices/add";

    }

    @PostMapping(value = "create")
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")

    public String saveNotice(Notice notice, HttpServletRequest request, RedirectAttributes model) {

        String[] vpaID = request.getParameterValues("vpa");
        logger.info("The submitted vpas " + vpaID.length);


        List<Long> skillIds = new ArrayList<Long>();
        if (vpaID.length > 0) {
            for (int i = 0; i < vpaID.length; i++) {
                skillIds.add(Long.parseLong(vpaID[i]));

            }
            List<Vpa> selectedSkills = vpaService.findAllById(skillIds);
            notice.setVpa(selectedSkills);
        }


        Notice notice1 = noticeService.save(notice);


        if (notice1 != null) {
            success.add("Successfully saved notice");
            model.addFlashAttribute("success", Utility.success(success));
            return "redirect:/notices";
        }

        return "notices/add";
    }


    @RequestMapping(value = "edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable Long id, HttpServletRequest request,Model model) {
        Notice notice = noticeService.findById(id);
        if (notice == null) {
            return "redirect:/notice/add";
        }

        ArrayList<UserType> userTypes = new ArrayList<UserType>(1);
        Set<String> noticeRoles = notice.getRoleNames();
        for (String s : noticeRoles) {
            logger.info(s);
            userTypes.add(UserType.valueOf(s));
        }
        List<Vpa> vpas = vpaService.findAllByUsertype(userTypes);

        Param p = Utility.getParam(request);

        p.setSort("name");


        model.addAttribute("stations", Accessor.findList(Station.class, Filter.get(), p));
        model.addAttribute("departments", Accessor.findList(Department.class, Filter.get(), p));

        p.setSort("acronym");
        model.addAttribute("agencies", Accessor.findList(Agency.class, Filter.get(), p));


        model.addAttribute("vendors", VdmsUtils.getVendorRoles());
        model.addAttribute("vpas", vpas);
        model.addAttribute("notice", notice);
        model.addAttribute("edit", "edit");

        return "notices/add";
    }


    @PostMapping(value = "edit/{id}")
    public String edit(@PathVariable Long id, Notice notice, BindingResult bindingResult, Model model) throws Exception {
        System.out.println("THe notice is " + notice.getId());
        Notice dbContract = noticeService.findById(id);
        //https://careers.un.org/lbw/jobdetail.aspx?id=80976


        if (dbContract == null) {
            noticeService.save(notice);
            return "redirect:/notices/" + notice.getUuid();
        }

        dbContract = notice;

        //updateContract(notice, dbContract);


        noticeService.save(dbContract);

        return "redirect:/notices/" + dbContract.getUuid();
    }


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
    public String index(HttpServletRequest request, Model model) {
        Param p = Utility.getParam(request);

        List<Notice> notices = Accessor.findList(Notice.class, Filter.get(), p);
        int total = notices.size();

        model.addAttribute("pager", new Pager(total, p.getPage(), p.getSize()));


        model.addAttribute("notices", notices);
        return "notices/index";
    }

    @GetMapping(value = "public")
    public String publicIndex(HttpServletRequest request, Model model) {
        Param p = Utility.getParam(request);
        List<Notice> notices;
        String type = request.getParameter("type");
        if (type != null) {

            notices = Accessor.findList(Notice.class, Filter.get().field("active", true).field("roles").contains(type), p);


        } else {
            notices = Accessor.findList(Notice.class, Filter.get().field("active", true), p);

        }
        int total = notices.size();


        model.addAttribute("pager", new Pager(total, p.getPage(), p.getSize()));


        model.addAttribute("count", total);

        model.addAttribute("pageSize", p.getSize());

        model.addAttribute("currentPage", p.getPage());

        model.addAttribute("from", p.getSize() * p.getPage() + 1);

        model.addAttribute("to", p.getSize() * (p.getPage() + 1));

        Pager pager = new Pager(total, p.getPage(), p.getSize(), p.getUrl());

        model.addAttribute("pager", pager);

        model.addAttribute("notices", notices);
        return "notices/public_index";
    }


    @RequestMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
    public String delete(@PathVariable Long id, Model model) {
        noticeService.deleteById(id);

        return "redirect:/notices";
    }

    @GetMapping(value = "{uuid}")
    public String publicIndex(@PathVariable String uuid, HttpServletRequest request, Model model) {


        if (uuid != null) {

            Notice notice = Accessor.findOne(Notice.class, Filter.get().field("uuid", uuid));

            model.addAttribute("notice", notice);
        }


        return "notices/public_details";
    }


    @RequestMapping("/{id}")
    public String viewNotice(@PathVariable Long id, Model model) {
        model.addAttribute("notice", Accessor.findOne(Notice.class, id));


        model.addAttribute("documentUploadForm", new DocumentUploadFormDto());
        // model.addAttribute("documents", Accessor.findList(ContractDocument.class, Filter.get().field("contract.id", id)));


        return "notices/contract";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN_ACCOUNT','STAFF','UNSTAFF')")
    @RequestMapping(value = "/changeStatus")
    public String modifyContractStatus(@ModelAttribute("notices") Notice modifiedContract, Model model, HttpServletRequest request) throws IllegalAccessException {

        System.out.println("ModifiedContract: " + modifiedContract);

        if (!Auth.INSTANCE.getAuth().isPresent()) {
            throw new IllegalAccessException("You need to be logged in to perform this operation");
        }

        Notice contract = Accessor.findOne(Notice.class, modifiedContract.getId());
        contract.setActive(modifiedContract.isActive());


        noticeService.save(contract);

        if ("page".equalsIgnoreCase(request.getParameter("rd"))) {
            return "redirect:/notices/" + contract.getId();
        }

        return "redirect:/notices";
    }


    @RequestMapping(value = "/documents")
    public String documents(Model model, HttpServletRequest request) {
        model.addAttribute("documents", Accessor.findList(ContractDocument.class, Filter.get()));
        model.addAttribute("reviewStatus", Arrays.asList(ReviewStatus.values()));

        return "notices/documents";
    }


    private Notice updateContract(Notice formContract, Notice dbContract) {
        if (formContract == null) {
            return dbContract;
        }

        if (formContract.getRoles() != null) {
            dbContract.setRoles(formContract.getRoles());
        }

        if (StringUtils.isNotBlank(formContract.getPosition())) {
            dbContract.setPosition(formContract.getPosition());
        }


        if (StringUtils.isNotBlank(formContract.getDetails())) {
            dbContract.setDetails(formContract.getDetails());
        }
        if ((formContract.isActive())) {
            dbContract.setActive(formContract.isActive());
        }


        if (formContract.getOpeningDate() != null) {
            dbContract.setOpeningDate(formContract.getOpeningDate());
        }

        if (formContract.getClosingDate() != null) {
            dbContract.setClosingDate(formContract.getClosingDate());
        }


        if (formContract.getLinkedUrl() != null) {
            dbContract.setLinkedUrl(formContract.getLinkedUrl());
        }


        return dbContract;
    }












    /*

    @PostMapping("upload")
    public String handleFileUpload(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = multipartResolver.resolveMultipart(request);
        UUID contactId = UUID.randomUUID();
        MultipartFile file = multipartRequest.getFile("image");
        UUID fileName = UUID.randomUUID();
        String questionID = request.getParameter("questionId");
        Notice question = noticeService.findById((Long.parseLong(questionID)));
        System.out.println("The Question submitted is : " + request.getParameter("questionId"));


        Document image = new Document();
        //image.setNotice(question);
        String imageFullPath = "";

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentType(file.getContentType());
        String fileExtension = "";
        int i = file.getOriginalFilename().lastIndexOf('.');
        if (userService.getEnvironmentProfile().name() == "PROD") {
            if (i > 0) {
                fileExtension = file.getOriginalFilename().substring(i + 1);


                imageFullPath = fileName.toString() + "." + fileExtension;


                awsS3Service.uploadFile(fileName.toString() + "." + fileExtension, file.getInputStream(), objectMetadata);
               */ /*
                 Return path and file name:
                 https://s3-us-west-2.amazonaws.com/studylabcentraluploads/6fd1c185-8fcc-406c-a87a-c3b82ca9e73d.jpg
                 */
           /* }
        } else {
            //  imageFullPath = storageService.store(file, question.getIdentifier());


        }


        return imageFullPath;
    }*/

}
