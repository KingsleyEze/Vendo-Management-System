package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by abdulhakim on 12/6/16.
 */


@Data
@Entity
@Table(name="states")

public class State  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")

    private Long id;

    public String name;
    public  String minLat ;
    public  String capital ;
    public  String latitude;
    public  String longitude;
    public  String minLong;
    public  String maxLat;
    public  String maxLong;

    @Transient
    public String waecStateCode;


    @ManyToOne
    public Country country;

    public  State(){}

    public void setName(String name){
        if(name != null){
            this.name =name.toUpperCase();
        }
    }
}
