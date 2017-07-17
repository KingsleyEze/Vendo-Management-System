package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Contract;
import org.ng.undp.vdms.domains.ContractDocument;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emmanuel on 6/13/17.
 */
public interface ContractDocumentRepository extends CrudRepository<ContractDocument, Long>, JpaSpecificationExecutor<ContractDocument> {

}
