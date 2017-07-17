package org.ng.undp.vdms.domains.evaluations;

/**
 * Created by macbook on 7/3/17.
 */

import org.ng.undp.vdms.domains.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

import lombok.Data;

import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.settings.Agency;
import org.ng.undp.vdms.domains.settings.Department;
import org.ng.undp.vdms.domains.settings.Station;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by macbook on 6/29/17.
 */
@Data
@Entity
@Table(name = "supplier_evaluations")
public class SupplierEvaluation

{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @ManyToOne
    private Supplier supplier;

    @OneToOne
    private Contract contract;

    public SupplierEvaluation() {

    }

    @Column(columnDefinition = "varchar(255) unique  not null ")
    private String contractNumber;


    @ManyToOne
    private Department organizationalUnit;

    @ManyToOne
    private Station station;

    @ManyToOne
    private Agency agency;

    @Column(columnDefinition = "text null")
    private String natureOfServicesProvided;

    private Date startDate;
    private Date endDate;


    private int qualityOfWork;
    private int technicalSkills;
    private int valueForMoney;
    private int overallPerformanceRating;


    @Column(columnDefinition = "text null")
    private String termsOfReference;

    @Column(columnDefinition = "text null")
    private String briefNarrative;


    private Date contractExtensionStart;
    private Date contractExtensionEnd;


    private boolean retentionOnTheContractorsDatabase= true;

    private Date contractTermination;

    @Column(columnDefinition = "text null")
    private String otherRecommendationRelatedToAssignment;

    @Column(columnDefinition = "text null")
    private String justificationForRequest;


    private String name;
    private String title;
    private Date certifyingDate;



    private Date created_at;

    private Date updated_at;


    private Date deleted_at;


    @PrePersist
    void createdAt() {
        this.created_at = this.updated_at = new Date();


    }

    @PreUpdate
    void updatedAt() {
        this.updated_at = new Date();


    }


}
