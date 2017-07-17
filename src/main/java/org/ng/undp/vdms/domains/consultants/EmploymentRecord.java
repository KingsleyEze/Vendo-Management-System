package org.ng.undp.vdms.domains.consultants;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ng.undp.vdms.domains.Consultant;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emmanuel on 6/19/17.
 */
@Getter
@Setter
@Entity
@Table(name = "employment_records")
public class EmploymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private EmploymentData employmentData;

    private Date startDate;

    private Date endDate;

    private boolean isPresent;

    private double startingSalary;

    private double finalSalary;

    private String jobTitle;

    @Lob
    private String jobDescription;

    private String typeOfBusiness;

    private String supervisorsName;

    private int noOfEmployeesSupervised;

    private String reasonForLeaving;

    private String nameOfEmployer;

    private String addressOfEmployer;

}
