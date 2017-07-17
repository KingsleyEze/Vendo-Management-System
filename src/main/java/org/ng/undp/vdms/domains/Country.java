package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

/**
 * Created by abdulhakim on 12/6/16.
 */


@Data
@Entity
@Table(name="countries")

public class Country  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")

    private Long id;

    @OneToMany
    @JsonIgnore
    public List<State> states;
    public String name;

    public String nativeName;

    public String[] tld;

    public String cca2;

    public String ccn3;

    public String cca3;

    public String[] currency;

    public String[] callingCode;

    public String capital;

    public String[] altSpellings;

    public String relevance;

    public String region;

    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="country_translations", joinColumns=@JoinColumn(name="country_id"))

    public Map<String, String> translations;

    public String subregion;

    public String[] language;

    public String[] languageCodes;



    public Long[] latlng;

    public String demonym;

    public String[] borders;

    public Integer area;

    public  Country(){}
    public void setName(String name){
        if(name != null){
            this.name =name.toUpperCase();
        }
    }
}
