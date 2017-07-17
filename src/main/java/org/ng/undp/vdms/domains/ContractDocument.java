package org.ng.undp.vdms.domains;

import lombok.Data;
import org.ng.undp.vdms.domains.constants.ReviewStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by emmanuel on 6/13/17.
 */
@Data
@Entity
@Table(name = "contract_documents")
public class ContractDocument extends Model {

   /* @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;*/

    @NotNull
    @ManyToOne(cascade = CascadeType.ALL)
    private Contract contract;

    private String name;

    private String fileName;

    @ManyToOne
    private DocumentType documentType;

    private String remarks;

    @ManyToOne
    private User reviewedBy;

    private boolean reviewed;

    @Enumerated(EnumType.STRING)
    private ReviewStatus reviewStatus;

    /*private Date created_at;

    private Date updated_at;

    @PrePersist
    void createdAt() {
        this.created_at = this.updated_at = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.updated_at = new Date();
    }
*/

}
