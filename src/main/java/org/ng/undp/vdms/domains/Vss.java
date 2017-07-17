package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.poi.util.StringUtil;
import org.ng.undp.vdms.domains.constants.UserType;
import org.thymeleaf.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by abdulhakim on 11/12/16.
 */
@Entity
@Table(name = "vsses")
@Getter
@Setter
public class Vss {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @JoinColumn(name = "vpa_id")
    private Vpa vpa;

    private UserType usertype;


    private Date created_at;

    private Date updated_at;

    @PrePersist
    void createdAt() {
        this.created_at = this.updated_at = new Date();
    }


    @PreUpdate
    void updatedAt() {
        if (this.created_at == null) {
            this.created_at = new Date();
        }
        this.updated_at = new Date();
    }

    public Vss() {
    }
}
