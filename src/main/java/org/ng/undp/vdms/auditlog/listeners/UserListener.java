package org.ng.undp.vdms.auditlog.listeners;

import org.ng.undp.vdms.domains.User;

import javax.persistence.*;

/**
 * Created by abdulhakim on 11/19/16.
 */
public class UserListener {
    @PrePersist
    public void userPrePersist(User ob) {
        System.out.println("Listening User Pre Persist : " + ob.getUsername());
    }
    @PostPersist
    public void userPostPersist(User ob) {
        System.out.println("Listening User Post Persist : " + ob.getUsername());
    }
    @PostLoad
    public void userPostLoad(User ob) {
        System.out.println("Listening User Post Load : " + ob.getUsername());
    }
    @PreUpdate
    public void userPreUpdate(User ob) {
        System.out.println("Listening User Pre Update : " + ob.getUsername());
    }
    @PostUpdate
    public void userPostUpdate(User ob) {
        System.out.println("Listening User Post Update : " + ob.getUsername());
    }
    @PreRemove
    public void userPreRemove(User ob) {
        System.out.println("Listening User Pre Remove : " + ob.getUsername());
    }
    @PostRemove
    public void userPostRemove(User ob) {
        System.out.println("Listening User Post Remove : " + ob.getUsername());
    }
}
