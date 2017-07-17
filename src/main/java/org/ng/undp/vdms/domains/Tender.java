package org.ng.undp.vdms.domains;

import lombok.Data;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import org.ng.undp.vdms.domains.constants.TenderStatus;
import org.ng.undp.vdms.utils.ShortUUID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by macbook on 6/16/17.
 */



@Entity
@Table(name = "tenders")
public class Tender extends Model {

    @NotNull
    @Column(unique = true)
    private String uuid;

    @ManyToOne
    private Notice notice;

    private  TenderStatus tenderStatus = TenderStatus.TO_BE_REVIEWED;


    public TenderStatus getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(TenderStatus tenderStatus) {
        this.tenderStatus = tenderStatus;
    }



    @OneToOne
    private  Vendor vendor;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Notice getNotice() {
        return notice;
    }

    public void setNotice(Notice notice) {
        this.notice = notice;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public List<Document> getAttachedDocument() {
        return attachedDocument;
    }

    public void setAttachedDocument(List<Document> attachedDocument) {
        this.attachedDocument = attachedDocument;
    }

    @Override
    public Date getDeleted_at() {
        return deleted_at;
    }

    @Override
    public void setDeleted_at(Date deleted_at) {
        this.deleted_at = deleted_at;
    }

    @Cascade(org.hibernate.annotations.CascadeType.DETACH)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @OneToMany
    @JoinColumn(name = "document_id")
    List<Document> attachedDocument;


    /*
    * This field is here to check whether a field is deleted or not.
    * by default, the field is NULL, if it carries a timestamp instead of null
    * then the record has been deleted,
    */
    private Date deleted_at;


     public Tender() {
    }


    @PrePersist
    void createAt() {

        this.uuid = ShortUUID.shortUUID();


    }


}
