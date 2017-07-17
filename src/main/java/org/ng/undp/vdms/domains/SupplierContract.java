package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "supplier_contracts")
@Data
public class SupplierContract extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "supplier_experience_id")
    private SupplierExperience supplierExperience;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String organization;

    @Column
    private String value;

    @Column
    private String year;

    @Column
    private String goodService;

    @Column
    private String destination;
}
