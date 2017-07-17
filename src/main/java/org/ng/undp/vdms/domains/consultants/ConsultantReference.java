package org.ng.undp.vdms.domains.consultants;

import lombok.Data;
import org.ng.undp.vdms.domains.Consultant;
import org.ng.undp.vdms.domains.constants.RelationshipType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emmanuel on 6/19/17.
 */
@Data
public class ConsultantReference {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private EmploymentData employmentData;

    private String fullName;

    private String fullAddress;

    private String businessOrOccupation;
}
