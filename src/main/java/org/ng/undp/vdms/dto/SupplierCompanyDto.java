package org.ng.undp.vdms.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/18/2017.
 */

@Data
public class SupplierCompanyDto {

    private Long id;

    private String company;

    private String street;

    private String postalCode;

    private String city;

    private String country;

    private String postalAddress;

    private String telephoneNumber;

    private String emailAddress;

    private String faxNumber;

    private String internetAddress;

    private String contactNameAndTitle;

    private String parentCompany;

    private MultipartFile subsidiaries;

    private String natureOfBusiness;

    private String natureOfBusinessOther;

    private String typeOfBusiness;

    private String typeOfBusinessOther;

    private String yearEstablished; //Use calendar plugin to protect and maintain.

    private String numberOfFullTimeEmployee;

    private String licenceNumberOrStateRegistered;

    private String vatNumberTaxIdentification;

    private MultipartFile supplierDocument;

    private String documentLanguage;

    private String documentLanguageOther;

    private String workingLanguage;

    private String workingLanguageOther;
}
