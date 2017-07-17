package org.ng.undp.vdms.domains.security;

import org.ng.undp.vdms.domains.Model;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by emmanuel on 12/1/16.
 */
@Getter
@Setter
@Entity
@Table(name="path_securities")
public class PathSecurity extends Model {

    @NotNull
    @Column(name = "resource", unique = true)
    private String name;

    @ManyToOne()
    @JoinColumn(name = "permission")
    private Permission permission;

    public void setName(String resource){
        if(StringUtils.isNotEmpty(resource)){
            this.name = resource.toLowerCase();
        }
    }


}
