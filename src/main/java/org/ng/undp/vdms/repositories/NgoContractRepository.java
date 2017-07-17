package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.NgoContract;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/18/2017.
 */

@Repository
public interface NgoContractRepository extends CrudRepository<NgoContract, Long>{
}
