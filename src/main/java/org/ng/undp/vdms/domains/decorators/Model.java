package org.ng.undp.vdms.domains.decorators;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Author: Kingsley Eze.
 * Date: 6/14/2017.
 */

@Data
@MappedSuperclass
public class Model implements Serializable {

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateAt;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @PrePersist
    void prePersist() {

        this.createdAt = this.updateAt = new Date();
    }

    @PreUpdate
    void preUpdate() {

        this.updateAt = new Date();
    }
}
