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
@Table(name="method_securities")
public class MethodSecurity extends Model {

    @NotNull
    @Column(name = "clazz")
    private Long clazz;

    @NotNull
    @Column(name = "method", unique = true)
    private String method;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "method_security_permissions", joinColumns = {
            @JoinColumn(name = "method_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id") })
    private Set<Permission> permissions = new HashSet<>();


}
