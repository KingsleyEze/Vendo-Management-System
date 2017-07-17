package org.ng.undp.vdms.domains.consultants;

import lombok.Data;
import org.ng.undp.vdms.domains.Consultant;
import org.ng.undp.vdms.domains.constants.RelationshipType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emmanuel on 6/19/17.
 */
@Entity
@Data
@Table(name= "consultant_relatives")
public class ConsultantRelative {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private BioData bioData;

    private String name;

    private Date dob;

    @Enumerated(EnumType.STRING)
    private RelationshipType relationship;

}
