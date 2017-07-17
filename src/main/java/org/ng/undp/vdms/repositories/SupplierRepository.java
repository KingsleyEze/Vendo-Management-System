package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Supplier;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by abdulhakim on 11/12/16.
 */
public interface SupplierRepository  extends CrudRepository<Supplier, Long>, JpaSpecificationExecutor<Supplier> {
}
