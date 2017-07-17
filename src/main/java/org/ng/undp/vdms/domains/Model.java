package org.ng.undp.vdms.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.Temporal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import lombok.Getter;
import lombok.Setter;
import org.ng.undp.vdms.utils.Auth;


@Getter
@Setter
@MappedSuperclass
public class Model implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date created_at = new Date();

    private Date updated_at = new Date();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "created_by")
    private User createdBy;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;


    @Temporal(TemporalType.TIMESTAMP)
    private Date deleted_at;

    @ManyToOne
    @JoinColumn(name = "deleted_by")
    private User deletedBy;


    @PrePersist
    void createdAt() {
        this.created_at = this.updated_at = new Date();
        Optional<User> optional = Auth.INSTANCE.getAuth();
        if(this.createdBy == null && optional.isPresent() ){
            createdBy = optional.get();
        }

    }

    @PreUpdate
    void updatedAt() {
        this.updated_at = new Date();

//        Optional<User> optional = Auth.INSTANCE.getAuth();
//        if( optional.isPresent() ){
//           updatedBy = optional.get();
//        }
    }


}




