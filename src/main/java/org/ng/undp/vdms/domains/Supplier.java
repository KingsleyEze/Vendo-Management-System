package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by abdulhakim on 11/12/16.
 */

@Entity
@Table(name="suppliers")
@Data
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @OneToOne(cascade = CascadeType.ALL)
    private  User user;

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
