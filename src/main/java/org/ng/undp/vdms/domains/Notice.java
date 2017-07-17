package org.ng.undp.vdms.domains;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.ng.undp.vdms.domains.security.Role;
import org.ng.undp.vdms.domains.settings.Agency;
import org.ng.undp.vdms.domains.settings.Department;
import org.ng.undp.vdms.domains.settings.Station;
import org.ng.undp.vdms.utils.Auth;
import org.ng.undp.vdms.utils.DateUtils;
import org.ng.undp.vdms.utils.ShortUUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by macbook on 6/16/17.
 */


@Data
@Entity
@Table(name = "notices")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Date created_at = new Date();

    private Date updated_at = new Date();


    @OneToOne
    private Agency agency;

    @OneToOne
    private Department department;
    @OneToOne
    private Station station;


    private boolean alertSent = false;

    @NotNull
    @Column(unique = true)
    private String uuid;

    private String position;
    private String linkedUrl;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "notice_roles",
            joinColumns = @JoinColumn(name = "notice_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))

    private Set<Role> roles;

    public Set<String> getRoleNames() {
        if (roles == null) {
            roles = new HashSet<>();
        }

        return roles.stream().map(p -> p.getName()).collect(Collectors.toSet());
    }

    @OneToMany

    private List<Vpa> vpa;


    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @OneToMany
    @JoinColumn(name = "document_id")
    List<Document> attachedDocument;

    @OneToMany

    private List<Tender> tenders;


    @Column(columnDefinition = "text")
    private String details;

    private boolean publish = true;

    private boolean active = true;
    private boolean requireUploadByVendor = false;

    private Date openingDate;

    private Date closingDate;


    public Notice() {
    }

    private Date deleted_at;


    @PrePersist
    void createAt() {

        this.uuid = ShortUUID.shortUUID();


        this.created_at = this.updated_at = new Date();
        checkDates();


    }

    @PreUpdate
    void updatedAt() {
        this.updated_at = new Date();
        checkDates();
    }

    private void checkDates() {
        if ((DateUtils.isPastDateFromToday(this.openingDate) == true) && (DateUtils.isPastDateFromToday(this.closingDate) == false)) {
            this.setActive(true);


        } else if ((DateUtils.isPastDateFromToday(this.openingDate) == false) && (DateUtils.isPastDateFromToday(this.closingDate) == false)) {
            this.setActive(true);


        } else if ((DateUtils.isPastDateFromToday(this.openingDate) == true) && (DateUtils.isPastDateFromToday(this.closingDate) == true)) {
            this.setActive(false);


        } else if ((DateUtils.isPastDateFromToday(this.openingDate) == false) && (DateUtils.isPastDateFromToday(this.closingDate) == true)) {
            this.setActive(false);


        }


    }
}
