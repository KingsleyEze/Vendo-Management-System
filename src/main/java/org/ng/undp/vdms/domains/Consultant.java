package org.ng.undp.vdms.domains;

import lombok.Data;
import org.joda.time.DateTime;
import org.apache.commons.lang3.StringUtils;
import org.ng.undp.vdms.domains.consultants.BioData;
import org.ng.undp.vdms.domains.consultants.EducationalData;
import org.ng.undp.vdms.domains.consultants.EmploymentData;
import org.ng.undp.vdms.utils.DateUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by abdulhakim on 11/12/16.
 */
@Entity
@Data
@Table(name = "consultants")
public class Consultant  {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private  User user;

    @OneToOne()
    @JoinColumn(name = "educational_data_id")
    private EducationalData educationalData;

    @OneToOne()
    @JoinColumn(name = "employment_data_id")
    private EmploymentData employmentData;

    @OneToOne()
    @JoinColumn(name = "bio_data_id")
    private BioData bioData;

    public Integer getYearsOfExperience(){
        EmploymentData data = this.getEmploymentData();
        if(null == data || null ==  data.getEmploymentRecords()){
            return  null;
        }

        Date earliest = data.getEmploymentRecords().stream().map(p -> p.getStartDate()).min((r1, r2) -> r1.compareTo(r2)).get();
        if(earliest == null){
            return null;
        }

        return DateTime.now().getYear() - new DateTime(earliest).getYear();
    }


    @Transient
    private String name;
    public   String getName(){
        if(this.getBioData() !=null){
            return  this.getBioData().getFirstName() + ' ' +  this.getBioData().getSurName() + " [" + this.getUser().getUuid() + ']' ;
        } else {
            return      " [" + ']' ;
        }
    }

    private Date created_at;

    private Date updated_at;

    @PrePersist
    void createdAt() {
        this.created_at = this.updated_at = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.updated_at = new Date();
    }



}
