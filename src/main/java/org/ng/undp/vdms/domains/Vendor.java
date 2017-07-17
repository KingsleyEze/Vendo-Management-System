package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.*;
import org.ng.undp.vdms.dao.Accessor;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.utils.ShortUUID;
import org.ng.undp.vdms.domains.User;
import org.springframework.boot.autoconfigure.web.ResourceProperties;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by macbook on 5/1/17.
 */


@Entity
@Table(name = "vendors")
@Getter
@Setter
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
    @Column(unique = true)
    private String uuid;

    private String name;

    @Transient
    private String vendorName;

    public String getVendorName() {


        return this.getUser().getFirstname() + " " + this.getUser().getLastname();

    }

    private  boolean profileCompleted = false;

    @Transient
    private String vendorType;

    public String getVendorType() {

        String role = "NULL";
        Set<String> roles = this.getUser().getRoleNames();

        for (String r : roles) {
            if (!r.toUpperCase().equals("VENDOR")) {
                return r.toString().toUpperCase();

            }
        }


        return role;
    }

    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @OneToMany
    @JoinColumn(name = "vpa_id")
    private List<Vpa> vpa;


    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @OneToMany
    @JoinColumn(name = "vss_id")
    private List<Vss> vss;

    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @OneToMany
    @JoinColumn(name = "skill_id")
    private List<Skill> skill;


    //@JoinColumn(name="user_uuid")
    @OneToOne(cascade = CascadeType.ALL)

    private org.ng.undp.vdms.domains.User user;


    private Date created_at;
    private Date updated_at;

    /*
     * This field is here to check whether a field is deleted or not.
     * by default, the field is NULL, if it carries a timestamp instead of null
     * then the record has been deleted,
     */
    private Date deleted_at;

    private boolean suspended = false;

    public Vendor() {
    }


    @PreUpdate
    void updatedAt() {
        this.updated_at = new Date();
    }


    @PrePersist
    void createAt() {

        this.setProfileCompleted(false);

        this.created_at = this.updated_at = new Date();
        this.uuid = ShortUUID.shortUUID();


    }


}
