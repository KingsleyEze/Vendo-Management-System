package org.ng.undp.vdms.domains.evaluations;

import lombok.Data;

import org.ng.undp.vdms.domains.Consultant;
import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.Country;
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
@Table(name = "ic_evaluations")
public class ConsultantEvaluation

{
    @ManyToOne
    private Consultant consultant;

    @OneToOne
    private Contract contract;

    public  ConsultantEvaluation(){

    }

    @Column(columnDefinition = "varchar(255) unique  not null ")
    private String projectNumber;

    private String projectTitle;

    @ManyToOne
    private Department department;

    @ManyToOne
    private Station station;

    @ManyToOne
    private Agency agency;

    @Column(columnDefinition = "text null")
    private String briefDescriptionofTaskCompleted;

    private Date startDate;
    private Date endDate;

    //@ElementCollection
    //@CollectionTable(name="consultant_countries_visited")

    @OneToMany
    private List<Country> countriesVisitedDuringAssignment;



    private int technicalExpertise;
    private int imagination;
    private int initiative;
    private int interpersonalSkills;
    private int qualityofReportSubmitted;
    private int timelinessOfReportSubmitted;

    private int linguisticSkills;

    @Transient
    private List<ConsultantLanguage> languages;

    @Column(columnDefinition = "text null")
    private String remark;

    private boolean restrictedAccess = false;

    private boolean keepInRoster = true;
    private String nameOfEvaluator;

    @ManyToOne
    private Department division;

    private String telephone;


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date created_at ;

    private Date updated_at ;



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
