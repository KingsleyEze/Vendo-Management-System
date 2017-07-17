package org.ng.undp.vdms.auditlog;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by abdulhakim on 11/19/16.
 */
@Entity
@Table(name="user_logins")
@Data
public class LoginEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;




    private String username;


    private Date logged_in;

    private Date logged_out;

    private Date created_at;

    private Date updated_at;


    public LoginEntity(){}
        public LoginEntity(String username){ this.username = username;}
    @PrePersist
    void createdAt() {
        this.created_at = this.updated_at = this.logged_in = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.updated_at =this.logged_out = new Date();
    }

}
