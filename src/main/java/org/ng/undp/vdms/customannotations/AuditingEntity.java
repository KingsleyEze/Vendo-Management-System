package org.ng.undp.vdms.customannotations;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by abdulhakim on 11/19/16.
 */
@Entity
@Table(name="auditing")
@Data
public class AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;
    private String username;
    private String user_type;
    private String action;
    private String target_user;

    private Date created_at;
    private Date updated_at;

    private String user_ip;

public AuditingEntity(){}
    public AuditingEntity(String username, String user_type, String action, String target_user, String user_ip){

        this.username= username;
        this.user_ip = user_ip;
        this.user_type =user_type;
        this.action = action;
        this.target_user = target_user;

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