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
@Table(name="roles")
public class Role extends Model {

    @NotNull(message = "Name Cannot be null")
    @Column(unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions", joinColumns = {
            @JoinColumn(name = "role_id") },
            inverseJoinColumns = { @JoinColumn(name = "permission_id") })
    private Set<Permission> permissions = new HashSet<>();

    @Lob
    @Column(name="description")
    private String description;

    private boolean active;

    @Override
    public String toString() {
        return "Role{" + "id=" + this.getId() + "name=" + name + ", description=" + description + ", active=" + active + '}';
    }
    
    

}