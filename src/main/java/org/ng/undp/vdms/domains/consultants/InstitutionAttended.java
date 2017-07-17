package org.ng.undp.vdms.domains.consultants;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ng.undp.vdms.domains.Consultant;
import org.ng.undp.vdms.domains.constants.InstitutionType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emmanuel on 6/19/17.
 */
@Getter
@Setter
@Entity
@Table(name = "institutions_attended")
public class InstitutionAttended {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private EducationalData educationalData;

    @Enumerated(EnumType.STRING)
    private InstitutionType institutionType;

    private String name;

    private String place;

    private String country;

    private Date startDate;

    private Date endDate;

    private String certificateObtained;

    private String grade;

    private String courseOfStudy;


}
