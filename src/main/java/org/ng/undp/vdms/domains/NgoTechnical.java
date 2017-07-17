package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "ngo_technicals")
@Data
public class NgoTechnical extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "ngoTechnical", fetch = FetchType.EAGER)
    private List<NgoGood> ngoGood;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "ngoTechnical")
    private List<NgoOffice> ngoOffices;

}
