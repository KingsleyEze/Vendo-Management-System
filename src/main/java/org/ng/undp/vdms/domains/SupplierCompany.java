package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "supplier_companies")
@Data
public class SupplierCompany extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String company;


    @Transient
    private String name;
    public   String getName(){
        return  this.getCompany()  + " [" + this.getUser().getUuid() + ']' ;
    }


    @Column
    private String street;

    @Column
    private String postalCode;

    @Column
    private String city;

    @Column
    private String country;

    @Column
    private String postalAddress;

    @Column
    private String telephoneNumber;

    @Column
    private String emailAddress;

    @Column
    private String faxNumber;

    @Column
    private String internetAddress;

    @Column
    private String contactNameAndTitle;

    @Column
    private String parentCompany;

    @Column
    private String natureOfBusiness;

    @Column
    private String natureOfBusinessOther;

    @Column
    private String typeOfBusiness;

    @Column
    private String typeOfBusinessOther;

    @Column
    private String yearEstablished; //Use calendar plugin to protect and maintain.

    @Column
    private String numberOfFullTimeEmployee;

    @Column
    private String licenceNumberOrStateRegistered;

    @Column
    private String vatNumberTaxIdentification;

    @Column
    private String documentLanguage;

    @Column
    private String documentLanguageOther;

    @Column
    private String workingLanguage;

    @Column
    private String workingLanguageOther;
}
