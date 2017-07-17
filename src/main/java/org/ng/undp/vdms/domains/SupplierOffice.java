package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/28/2017.
 */

@Entity
@Table(name = "supplier_offices")
@Data
public class SupplierOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "supplier_technical_id")
    private SupplierTechnical supplierTechnical;

    @Column
    private String company;

    @Column
    private String country;
}
