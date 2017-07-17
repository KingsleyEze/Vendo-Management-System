package org.ng.undp.vdms.domains.evaluations;

import lombok.Data;
import org.ng.undp.vdms.domains.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by macbook on 6/30/17.
 */
@Data
@Entity
@Table(name = "ic_evaluation_languages")
public class ConsultantLanguage{

    @ManyToOne
    private ConsultantEvaluation consultantEvaluation;
    private String language;
    private int spoken;
    private int written;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date created_at  ;
    private Date updated_at ;



    private Date deleted_at;


    @PrePersist
    void createdAt() {
        this.created_at = this.updated_at = new Date();


    }

    @PreUpdate
    void updatedAt() {
        this.updated_at = new Date();


    }
}
