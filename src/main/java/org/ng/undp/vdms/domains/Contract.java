package org.ng.undp.vdms.domains;

import lombok.Data;
import org.ng.undp.vdms.domains.constants.Status;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by emmanuel on 6/10/17.
 */
@Data
@Entity
@Table(name = "contracts")
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String details;

    @ManyToOne
    @JoinColumn(name="vendor_id")
    private Vendor vendor;

    @Transient
    private Long vendorId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date contractDate;

    private Status status;

    private BigDecimal amount;

    @ElementCollection
    private List<String> tags;

    @ManyToOne
    @JoinColumn(name="assigned_to_id")
    private User assignedTo;

    @ManyToOne
    private User statusChangedBy;

    private Date statusChangedAt;

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
