package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by abdulhakim on 12/6/16.
 */
@Data
@Entity
@Table(name = "districts")
public class District {

    @ManyToOne
    public State state;


    public  District(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name" )
    private String name;


    public void setName(String name){
        if(name != null){
            this.name =name.toUpperCase();
        }
    }


    public  String minLat ;

    public  String latitude;
    
    public  String longitude;
    
    public  String minLong;
    
    public  String maxLat;
    
    public  String maxLong;

    @Column(name = "created_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updated_at;

    @Version
    @Column(name = "version")
    private Long version;
    @PrePersist
    void createdAt() {
        this.created_at = this.updated_at = new Date();
    }

    @PreUpdate
    void updatedAt() {
        this.updated_at = new Date();
    }
}
