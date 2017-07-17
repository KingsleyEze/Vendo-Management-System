package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by macbook on 6/11/17.
 */
@Data
@Entity
@Table(name = "faqs")

public class Faq {


    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)

    private Long id;


    private String question;
    private String answer;

    private boolean publish = true;


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

