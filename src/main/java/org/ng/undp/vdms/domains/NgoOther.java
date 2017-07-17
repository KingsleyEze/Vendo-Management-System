package org.ng.undp.vdms.domains;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "ngo_others")
@Data
public class NgoOther extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "ngo_dispute1")
    private String ngoDispute1;

    @Column(name = "ngo_dispute2")
    private String ngoDispute2;

    @Column(name = "ngo_dispute3")
    private String ngoDispute3;

    @OneToMany(mappedBy = "ngoOther")
    private List<NgoMembership> ngoMemberships;


    @Column(name = "functional_title")
    private String functionalTitle;

    @Column(name = "signature")
    private String signature;
}
