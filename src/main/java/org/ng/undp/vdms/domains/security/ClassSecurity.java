package org.ng.undp.vdms.domains.security;

import org.ng.undp.vdms.domains.Model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by emmanuel on 12/1/16.
 */
@Getter
@Setter
@Entity
@Table(name="class_securities")
public class ClassSecurity extends Model {

    @NotNull
    @Column(name = "name", unique = true)
    private String name;


    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "class_security_permissions", joinColumns = {
            @JoinColumn(name = "class_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id") })
    private Set<Permission> permissions = new HashSet<>();
}
