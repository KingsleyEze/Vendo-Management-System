package org.ng.undp.vdms.domains;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.ng.undp.vdms.domains.constants.UserType;

import lombok.Data;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;
import org.ng.undp.vdms.domains.constants.UserType;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by macbook on 3/29/17.
 */


/**
 * Created by abdulhakim on 11/12/16.
 */
@Data
@Entity
//@Audited
@Table(name = "vendorskills")
public class Skill {






        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        private String name;
        private String description;





        private Date created_at;
        private Date updated_at;


        public Skill() {
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
