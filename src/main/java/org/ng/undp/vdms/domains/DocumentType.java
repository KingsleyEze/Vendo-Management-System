package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by emmanuel on 6/13/17.
 */
@Data
@Entity
@Table(name = "document_types")
public class DocumentType extends Model{

    @Column(name="name", unique = true)
    private String name;

    private String niceName;

    private String description;

}
