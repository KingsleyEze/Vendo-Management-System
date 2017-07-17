package org.ng.undp.vdms.dto;

import lombok.Data;
import org.ng.undp.vdms.domains.NgoGood;
import org.ng.undp.vdms.domains.NgoOffice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Data
public class NgoTechnicalDto {

    private Long id;

    private String ngoCompanyArray;

    private String ngoGoodArray;

    private List<NgoGood> ngoGood;

    private List<NgoOffice> ngoOffices;

    private MultipartFile ngoDocument;

}
