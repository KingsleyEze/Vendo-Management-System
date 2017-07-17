package org.ng.undp.vdms.services;

import org.apache.poi.ss.formula.functions.T;
import org.ng.undp.vdms.domains.Notice;
import org.ng.undp.vdms.domains.Tender;
import org.ng.undp.vdms.repositories.NoticeRepository;
import org.ng.undp.vdms.repositories.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by macbook on 6/16/17.
 */

@Service
public class TenderService {
    @Autowired
    private TenderRepository noticeRepository;



    public Iterable<Tender> findAll() {

        return noticeRepository.findAll();
    }

    public  Tender  findById(Long id) {

        return noticeRepository.findOne(id);
    }

    public Tender getNotice(Long id) {
        return noticeRepository.findOne(id);
    }

    public Tender editNotice(Tender vpa) {
        return noticeRepository.save(vpa);
    }

    public void delete(Long id) {
        noticeRepository.delete(id);
    }
    public Tender save(Tender d) {
        return noticeRepository.save(d);
    }
}
