package org.ng.undp.vdms.domains.settings;

import lombok.Data;
import org.ng.undp.vdms.domains.Model;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by macbook on 6/25/17.
 */


@Entity
@Table(name = "stations")
public class Station extends Model {
    private  String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
