package org.ng.undp.vdms.dto;

import lombok.Data;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Data
public class NgoProjectDto {

    private Long id;

    private String projectName;

    private CountryDto country;

    private String year;
}
