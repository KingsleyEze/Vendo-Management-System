package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "supplier_others")
@Data
public class SupplierOther extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "supplier_dispute1")
    private String supplierDispute1;

    @Column(name = "supplier_dispute2")
    private String supplierDispute2;

    @Column(name = "supplier_dispute3")
    private String supplierDispute3;

    @OneToMany(mappedBy = "supplierOther")
    private List<SupplierMembership> supplierMemberships;


    @Column(name = "functional_title")
    private String functionalTitle;

    @Column(name = "signature")
    private String signature;
}
