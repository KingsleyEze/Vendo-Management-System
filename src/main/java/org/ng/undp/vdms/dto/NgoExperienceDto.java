package org.ng.undp.vdms.dto;

import lombok.Data;
import org.ng.undp.vdms.domains.NgoContract;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Data
public class NgoExperienceDto {

    private Long id;

    private String ngoContractArray;

    private String ngoProject1;

    private String ngoCountry1;

    private String ngoProject2;

    private String ngoCountry2;

    private String ngoProject3;

    private String ngoCountry3;

    private MultipartFile ngoDocument;

    private boolean isEDIEnabled;

    private List<NgoContract> ngoContracts;
}
