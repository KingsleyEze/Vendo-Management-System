package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "supplier_projects")
@Data
public class SupplierProject extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String projectName;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @Column
    private String year;
}
