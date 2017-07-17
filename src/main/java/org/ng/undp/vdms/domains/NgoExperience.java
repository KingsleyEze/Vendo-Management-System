package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "ngo_experiences")
@Data
@EqualsAndHashCode(callSuper = false)
public class NgoExperience extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @OneToMany(mappedBy = "ngoExperience", fetch = FetchType.EAGER)
    private List<NgoContract> ngoContracts;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String ngoProject1;
    @Column
    private String ngoCountry1;
    @Column
    private String ngoProject2;
    @Column
    private String ngoCountry2;
    @Column
    private String ngoProject3;
    @Column
    private String ngoCountry3;

    @Column
    private boolean isEDIEnabled;
}
