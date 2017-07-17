package org.ng.undp.vdms.domains.security;

import org.ng.undp.vdms.domains.Model;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Objects;

/**
 * Created by emmanuel on 12/1/16.
 */
@Getter
@Setter
@Entity

@Table(name="permissions")
public class Permission extends Model {

    @Column(name = "name", unique = true)
    private String name;

    @Lob
    @Column(name="description")
    private String description;

    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(active, that.active) &&
                Objects.equals(name, that.name) && Objects.equals(this.getId(), that.getId());
    }

   /* public int compareTo(Object o){
        System.out.println("In side compareTo");
        Permission that = (Permission) o;

        return this.getId().compareTo(((Permission) o).getId());

    }*/

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, active);
    }

    public String toString(){
        return this.getClass().getSimpleName()+"name:"+this.name+"id:"+this.getId();
    }
}
