package org.ng.undp.vdms.controllers;

import org.apache.log4j.Logger;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;

import org.ng.undp.vdms.domains.DocumentType;

import org.ng.undp.vdms.repositories.DocumentTypeRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Created by emmanuel on 6/16/17.
 */
@Controller
@RequestMapping("/docTypes")
public class DocumentTypeController {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    private static final Logger LOGGER = org.apache.log4j.Logger.getLogger(DocumentTypeController.class);

    @RequestMapping()
    public String index(Model model){
        model.addAttribute("docTypes", Accessor.findList(DocumentType.class, Filter.get()));
        return "docTypes/index";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String addForm(Model model){

        model.addAttribute("docType", new DocumentType());
        return "docTypes/add";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String add(@Valid DocumentType documentType, Model model){
        documentTypeRepository.save(documentType);
        return "redirect:/docTypes";
    }

}
