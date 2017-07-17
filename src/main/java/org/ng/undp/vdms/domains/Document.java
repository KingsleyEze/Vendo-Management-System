package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by macbook on 6/16/17.
 */

@Data
@Entity
@Table(name = "documents")
public class Document extends  Model{

    private String filename;


    private String localFilename;

    private boolean active = true;



}
