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
@Entity
public class ConsultantRelativeWorkingInternationally {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Consultant consultant;

    private String name;

    @Enumerated(EnumType.STRING)
    private RelationshipType relationship;

    private String nameOfOrganisation;


}