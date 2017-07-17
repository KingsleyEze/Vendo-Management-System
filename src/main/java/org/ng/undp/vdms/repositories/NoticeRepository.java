package org.ng.undp.vdms.repositories;

import org.ng.undp.vdms.domains.Document;
import org.ng.undp.vdms.domains.Notice;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by macbook on 6/16/17.
 */
public interface NoticeRepository extends CrudRepository<Notice, Long>, JpaSpecificationExecutor<Notice> {

    public Iterable<Notice> findAll();


    public void deleteById(Long id);
    public  Notice  findOneByUuid(String uuid);

}
