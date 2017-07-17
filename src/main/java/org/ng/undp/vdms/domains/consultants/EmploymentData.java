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
@Table(name = "employment_data")
public class EmploymentData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private boolean canWeContactPresentEmployer;

    private boolean haveYouBeingACivilServantBefore;

    private String periodOfCivilService;

    @Lob
    private String relevantEmploymentFact;

    private boolean haveYouBeingConvictedBefore;

    private String convictionDetails;

    private boolean iCertifyThatMyResponsesAreTrue;


    @Transient
    List<EmploymentRecord> employmentRecords;

    private Long consultantId;

    public List<EmploymentRecord> getEmploymentRecordsFromDb(){
        return Accessor.findList(EmploymentRecord.class, Filter.get().field("employmentData.id", this.getId()));
    }
}
