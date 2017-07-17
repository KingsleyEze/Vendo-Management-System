package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "supplier_experiences")
@Data
@EqualsAndHashCode(callSuper = false)
public class SupplierExperience  extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "supplierExperience", fetch = FetchType.EAGER)
    private List<SupplierContract> supplierContracts;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String supplierProject1;
    @Column
    private String supplierCountry1;
    @Column
    private String supplierProject2;
    @Column
    private String supplierCountry2;
    @Column
    private String supplierProject3;
    @Column
    private String supplierCountry3;

    @Column
    private boolean isEDIEnabled;
}
