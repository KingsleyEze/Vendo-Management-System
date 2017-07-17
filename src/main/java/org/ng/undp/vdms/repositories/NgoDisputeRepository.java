package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.NgoDispute;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/18/2017.
 */

@Repository
public interface NgoDisputeRepository extends CrudRepository<NgoDispute, Long> {
}
