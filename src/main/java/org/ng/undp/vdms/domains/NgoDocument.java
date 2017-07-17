package org.ng.undp.vdms.domains;

import lombok.Data;
import org.ng.undp.vdms.constants.NgoDocumentType;

import javax.persistence.*;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/16/2017.
 */

@Entity
@Table(name = "ngo_documents")
@Data
public class NgoDocument extends Model{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column
    private NgoDocumentType ngoDocumentType;
}
