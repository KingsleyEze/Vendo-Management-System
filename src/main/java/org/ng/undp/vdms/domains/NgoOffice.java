package org.ng.undp.vdms.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/28/2017.
 */

@Entity
@Table(name = "ngo_offices")
@Data
public class NgoOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "ngo_technical_id")
    private NgoTechnical ngoTechnical;

    @Column
    private String company;

    @Column
    private String country;
}
