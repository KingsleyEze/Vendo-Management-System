package org.ng.undp.vdms.domains.settings;

import lombok.Data;
import org.ng.undp.vdms.domains.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Created by macbook on 6/25/17.
 */


@Entity
@Table(name = "agencies")
public class Agency extends Model {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    private  String name;
    @Column(unique = true,columnDefinition="character varying (20) unique not null ",length=20,nullable=false)

    private  String acronym;


}
