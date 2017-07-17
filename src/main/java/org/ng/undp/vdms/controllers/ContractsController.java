package org.ng.undp.vdms.controllers;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.*;
import org.ng.undp.vdms.domains.constants.ReviewStatus;
import org.ng.undp.vdms.domains.constants.Status;
import org.ng.undp.vdms.dto.DocumentUploadFormDto;
import org.ng.undp.vdms.repositories.AdditionalContractCostRepository;
import org.ng.undp.vdms.repositories.ContractDocumentRepository;
import org.ng.undp.vdms.services.ContractService;
import org.ng.undp.vdms.storage.StorageService;
import org.ng.undp.vdms.utils.Auth;
import org.ng.undp.vdms.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

/**
 * Created by emmanuel on 6/10/17.
 */
@Controller
@RequestMapping("/contracts")
@Transactional
public class ContractsController {

    @Autowired
    private StorageService storageService;

    @Autowired
    private ContractService contractService;

    @Autowired
    private ContractDocumentRepository contractDocumentRepository;

    @Autowired
    private AdditionalContractCostRepository additionalContractCostRepository;

    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(ContractsController.class);

    @RequestMapping()
    public String index(Model model){
        model.addAttribute("contracts", Accessor.findList(Contract.class, Filter.get()));
        return "contracts/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addForm(Model model){
        model.addAttribute("vendors", Accessor.findList(Vendor.class, Filter.get()));
        model.addAttribute("staffs", Accessor.findList(User.class, Filter.get()));
        model.addAttribute("contract", new Contract());
        return "contracts/add";
    }


    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String editForm(@PathVariable Long id,  Model model){
        Contract contract =  Accessor.findOne(Contract.class, id);
        if(contract == null){
            return "redirect:/contracts/add";
        }

        model.addAttribute("vendors", Accessor.findList(Vendor.class, Filter.get()));
        model.addAttribute("staffs", Accessor.findList(User.class, Filter.get()));
        model.addAttribute("contract", contract);
        model.addAttribute("edit", "edit");

        return "contracts/add";
    }



    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@Valid Contract contract, Model model){

        if(null != contract.getVendorId()) {
            Vendor v = Accessor.findOne(Vendor.class, contract.getVendorId());
            contract.setVendor(v);
        }
        contractService.save(contract);
        return "redirect:/contracts/" + contract.getId();
    }


    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String edit(@Valid Contract formContract, Model model){

        Contract dbContract = Accessor.findOne(Contract.class, formContract.getId());

        if(dbContract == null){
            contractService.save(formContract);
            return "redirect:/contracts/" + formContract.getId();
        }

        Contract contract = updateContract(formContract, dbContract);
        contractService.save(contract);

        return "redirect:/contracts/" + formContract.getId();
    }


    private Contract updateContract(Contract formContract, Contract dbContract){
        if(formContract == null ){
            return dbContract;
        }

        if(formContract.getAssignedTo() != null){
            dbContract.setAssignedTo(formContract.getAssignedTo());
        }

        if(StringUtils.isNotBlank(formContract.getName())){
            dbContract.setName(formContract.getName());
        }

        if((formContract.getStatus() != null)){
            dbContract.setStatus(formContract.getStatus());
        }

        if(formContract.getContractDate() != null){
            dbContract.setContractDate(formContract.getContractDate());
        }

        if(StringUtils.isNotBlank(formContract.getDetails())){
            dbContract.setDetails(formContract.getDetails());
        }

        if(formContract.getVendor() != null){
            dbContract.setVendor(formContract.getVendor());
        }

        if(CollectionUtils.isNotEmpty(formContract.getTags())){
            dbContract.setTags(formContract.getTags());
        }

        return  dbContract;
    }

    @RequestMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Model model){
        contractService.deleteById(id);

        return "redirect:/contracts";
    }

    @RequestMapping("/{id}")
    public String viewContract(@PathVariable Long id, Model model){
        model.addAttribute("contract", Accessor.findOne(Contract.class, id));
        model.addAttribute("docTypes", Accessor.findList(DocumentType.class, Filter.get()));
        model.addAttribute("allStatus", Status.asList());
        model.addAttribute("documentUploadForm", new DocumentUploadFormDto());
        model.addAttribute("documents", Accessor.findList(ContractDocument.class, Filter.get().field("contract.id", id)));
        model.addAttribute("reviewStatus", Arrays.asList(ReviewStatus.values()));
        model.addAttribute("additionalCosts", Accessor.findList(AdditionalContractCost.class, Filter.get().field("contract.id", id)));

        return "contracts/contract";
    }

    @Secured(value = "ADMIN")
    @RequestMapping(value = "/changeStatus")
    public String modifyContractStatus(@ModelAttribute("contract") Contract modifiedContract, Model model, HttpServletRequest request) throws IllegalAccessException{

        System.out.println("ModifiedContract: " + modifiedContract);

        if(!Auth.INSTANCE.getAuth().isPresent()){
            throw new IllegalAccessException("You need to be logged in to perform this operation");
        }

        Contract contract = Accessor.findOne(Contract.class, modifiedContract.getId());
        contract.setStatus(modifiedContract.getStatus());
        contract.setStatusChangedBy(Auth.INSTANCE.getAuth().get());
        contract.setStatusChangedAt(new Date());

        contractService.save(contract);

        if("page".equalsIgnoreCase(request.getParameter("rd"))){
            return "redirect:/contracts/" + contract.getId();
        }

        return "redirect:/contracts";
    }

    @Secured(value = "ADMIN")
    @RequestMapping(value = "/addAdditionalCost")
    public String addAdditionalCostToContrat(@ModelAttribute("additionalCost") AdditionalContractCost additionalCost , Model model, HttpServletRequest request) throws IllegalAccessException{

        if(!Auth.INSTANCE.getAuth().isPresent()){
            throw new IllegalAccessException("You need to be logged in to perform this operation");
        }

        Contract contract = additionalCost.getContract();
        System.out.println("Additional Cost : " + additionalCost);

        additionalContractCostRepository.save(additionalCost);

        return "redirect:/contracts/" + contract.getId();

    }


    @RequestMapping(value = "/documents")
    public String documents(Model model,  HttpServletRequest request){
        model.addAttribute("documents", Accessor.findList(ContractDocument.class, Filter.get()));
        model.addAttribute("reviewStatus", Arrays.asList(ReviewStatus.values()));

        return "contracts/documents";
    }


    @Secured(value = "ADMIN")
    @RequestMapping(value = "/documents/review")
    public String reviewDocument(@ModelAttribute("doc") ContractDocument contractDocument, Model model, HttpServletRequest request) throws IllegalAccessException{

        if(!Auth.INSTANCE.getAuth().isPresent()){
            throw new IllegalAccessException("You need to be logged in to perform this operation");
        }

        ContractDocument document = Accessor.findOne(ContractDocument.class, contractDocument.getId());
        document.setReviewStatus(contractDocument.getReviewStatus());
        document.setRemarks(contractDocument.getRemarks());
        document.setReviewedBy(Auth.INSTANCE.getAuth().get());
        document.setReviewed(true);
        contractDocumentRepository.save(document);

        if("page".equalsIgnoreCase(request.getParameter("rd"))){
            return "redirect:/contracts/" + document.getContract().getId();
        }

        return "redirect:/contracts/documents";
    }



    @RequestMapping(value = "/uploads")
    public String uploadLandlordDocs(@Valid DocumentUploadFormDto uploadForm, BindingResult result,  Model map, HttpServletRequest req) throws IOException, Exception {

        System.out.println("Doc Upload: " + uploadForm);
        List<MultipartFile> files = uploadForm.getFiles();
        List<Long> docTypes = uploadForm.getDocTypeIds();

        Contract contract = Accessor.findOne(Contract.class, uploadForm.getContractId());

        //Get a list of Documents uploaded for this Contract
        List<ContractDocument> l = Accessor.findList(ContractDocument.class, Filter.get().field("contract.id", uploadForm.getContractId()));


        String rootPath = req.getServletContext().getRealPath("/");//Application root Path

        for (MultipartFile file : files) {

            if (file != null && !file.isEmpty()) {

                DocumentType docType = Accessor.findOne(DocumentType.class, (long)docTypes.get(files.indexOf(file)));

                //File f = prepareFile(file);

                String fileName = prepareFile(file);
                //Should we use s3
                /*uploadService.uploadToS3("appimages.tolet.com.ng", fileUrl, f);
                System.out.println("uploading " + f.getName() + "...");
                System.out.println(up.getProgress());
                f.delete();*/

                //add conditionals to determine if the file type already existed or not.
                /*if (containsDocType(l, docType)) {
                    System.out.println("Modifying file because DocType already exists." + docType);
                    ContractDocument u = Accessor.findOne(ContractDocument.class, Filter.get().field("documentType.id", docType.getId()));
                    u.setUpdated_at(new Date());
                    u.setUpdatedBy(Auth.INSTANCE.getAuth().get());
                    Accessor.saveOne(u);
                    ;//Update the modification Information
                    continue;
                }*/


                System.out.println("Persisting new File information.");
                ContractDocument u = new ContractDocument();
                u.setFileName(fileName);
                u.setDocumentType(docType);
                u.setReviewStatus(ReviewStatus.UNAPPROVED);
                //u.setName();
                u.setContract(contract);
                Accessor.saveOne(u);

            }
        }

        return "redirect:/contracts/" + contract.getId();//To be changed later
    }




    private boolean containsDocType(List<ContractDocument> list, DocumentType d) {
        //The algorithm tries to utilize the fastest way to return a result,
        //instead of iterating through the whole list
        for (ContractDocument l : list) {
            if (l.getDocumentType() == d) {
                return true;
            }
        }
        return false;
    }

    private String prepareFile(MultipartFile file) throws IOException, Exception{
        if (file == null || file.isEmpty()) {
            LOGGER.info("Empty file.");
            //return getErrorMap("Error", "File is empty");
            throw new Exception("Submitted file should not be empty");
        }

        String fileName = UUID.randomUUID().toString();
        return storageService.store(file, fileName);
    }

}