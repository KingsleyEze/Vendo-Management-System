package org.ng.undp.vdms.domains.consultants;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ng.undp.vdms.domains.Consultant;

import javax.persistence.*;

/**
 * Created by emmanuel on 6/19/17.
 */
@Getter
@Setter
@Entity
@Table(name = "language_skills")
public class LanguageSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private  BioData bioData;

    private String language;

    private Boolean readEasily;

    private Boolean writeEasily;

    private Boolean speakFluently;

    private Boolean understandEasily;

}
