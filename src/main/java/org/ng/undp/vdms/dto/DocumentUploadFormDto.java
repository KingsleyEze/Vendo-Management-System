package org.ng.undp.vdms.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by emmanuel on 6/13/17.
 */
@Data
public class DocumentUploadFormDto {

    private List<Long> docTypeIds;

    private List<MultipartFile> files;

    private Long contractId;
}
