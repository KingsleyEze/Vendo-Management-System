package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "supplier_technicals")
@Data
public class SupplierTechnical  extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "supplierTechnical", fetch = FetchType.EAGER)
    private List<SupplierGood> supplierGood;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "supplierTechnical")
    private List<SupplierOffice> supplierOffices;

    @Override
    public String toString() {
        return "SupplierTechnical{" +
                ", user=" + user +
                ", supplierGood=" + supplierGood +
                ", supplierOffices=" + supplierOffices +
                '}';
    }
}
