package org.ng.undp.vdms.domains.consultants;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by emmanuel on 6/19/17.
 */
@Data
@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String addressLine;

    private String telNo;

    private String faxNo;

    private String email;


}
