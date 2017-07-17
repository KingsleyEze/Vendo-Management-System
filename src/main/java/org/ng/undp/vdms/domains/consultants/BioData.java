package org.ng.undp.vdms.domains.consultants;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ng.undp.vdms.constants.MaritalStatus;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;
import org.ng.undp.vdms.domains.constants.Sex;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by emmanuel on 6/19/17.
 */
@Getter
@Setter
@Table(name = "bio_data")
@Entity
public class BioData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String surName;

    private String firstName;

    private String middleName;





    private String maidenName;

    private Date dob;

    private String placeOfBirth;

    @ElementCollection
    private List<String> nationalitiesAtBirth;

    @ElementCollection
    private List<String> presentNationalities;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    private double height;

    private double weight;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    @ManyToOne
    private Address permanentAddress;

    @ManyToOne
    private Address presentAddress;

    @ManyToOne
    private Address officeAddress;

    private boolean hasSpouse;

    /*Have you taken any permanet status in any country that is not you Nationality*/
    private boolean hasForeignPermanentStatus;

    /*Country where you took Permanent Status that is not your Nationality*/
    private String countryOfForeignPermanentStatus;

    /*Are any of your relatives employed by a public international organization?*/
    private boolean hasAnyRelationWorkingInternationally;

    private String prefferedFieldOfWork;

    private String motherTongue;

    //capture Language skills
    @Transient
    private List<LanguageSkill> languageSkills;

    public List<LanguageSkill> getLanguageSkillsFromDb(){
        return Accessor.findList(LanguageSkill.class, Filter.get().field("bioData.id", this.getId()));
    }

    //Item 19 from form is skipped (Clerical grades)

    private boolean everTriedChangingPresentNationality;

    @Lob
    private String reasonForAttemptingToChangeNationality;

}
