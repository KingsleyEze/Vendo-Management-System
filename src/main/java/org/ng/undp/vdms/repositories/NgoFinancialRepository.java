package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.NgoFinancial;
import org.ng.undp.vdms.domains.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/18/2017.
 */

@Repository
public interface NgoFinancialRepository extends CrudRepository<NgoFinancial, Long>{

    NgoFinancial findByUser(User user);
}
