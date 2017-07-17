package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.ng.undp.vdms.domains.constants.UserType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Created by abdulhakim on 11/12/16.
 */
@Getter
@Setter
@Entity
//@Audited
@Table(name = "vpas")
public class Vpa {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "vpa", fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.DETACH)
    private List<Vss> vsses;


    private UserType usertype;

    private Date created_at;
    private Date updated_at;


    public Vpa() {
    }



    @PrePersist
    void createdAt() {
        this.created_at = this.updated_at = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.updated_at = new Date();
    }
}
