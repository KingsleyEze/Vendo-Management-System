package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "ngo_memberships")
@Data
public class NgoMembership extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ngo_other_id")
    private NgoOther ngoOther;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String organisationName;
}
