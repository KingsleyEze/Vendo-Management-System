package org.ng.undp.vdms.services;

import org.ng.undp.vdms.domains.Document;
import org.ng.undp.vdms.domains.Notice;
import org.ng.undp.vdms.domains.Vpa;
import org.ng.undp.vdms.repositories.DocumentRepository;
import org.ng.undp.vdms.repositories.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by macbook on 6/16/17.
 */

@Service
public class NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;



    public Iterable<Notice> findAll() {

        return noticeRepository.findAll();
    }

    public  Notice  findById(Long id) {

        return noticeRepository.findOne(id);
    }
    public  Notice  findOneByUuid(String uuid) {

        return noticeRepository.findOneByUuid(uuid);
    }

    public Notice getNotice(Long id) {
        return noticeRepository.findOne(id);
    }

    public Notice editNotice(Notice vpa) {
        return noticeRepository.save(vpa);
    }

    public void delete(Long id) {
        noticeRepository.delete(id);
    }
    public Notice save(Notice d) {
        return noticeRepository.save(d);
    }

    public void deleteById(Long id){
        noticeRepository.delete(id);
    }
}
