package org.ng.undp.vdms.domains.consultants;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.dao.Filter;

import javax.persistence.*;
import java.util.List;

/**
 * Created by emmanuel on 6/19/17.
 */
@Getter
@Setter
@Entity
@Table(name = "educational_data")
public class EducationalData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Transient
    private List<InstitutionAttended> institutionsAttended;

    public List<InstitutionAttended> getInstitutionsAttendedFromDb(){
        return Accessor.findList(InstitutionAttended.class, Filter.get().field("educationalData.id", this.getId()));
    }

    private Long consultantId;

    @ElementCollection
    private List<String> professionalActivitiesAndSocieties;

    @ElementCollection
    private List<String> professionalPublications;

 }
