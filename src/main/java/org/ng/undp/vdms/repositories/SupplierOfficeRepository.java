package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.SupplierOffice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/28/2017.
 */

@Repository
public interface SupplierOfficeRepository extends CrudRepository<SupplierOffice, Long> {
}
