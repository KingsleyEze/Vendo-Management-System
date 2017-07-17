package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Contract;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by emmanuel on 6/10/17.
 */
public interface ContractRepository extends CrudRepository<Contract, Long>, JpaSpecificationExecutor<Contract> {

}
