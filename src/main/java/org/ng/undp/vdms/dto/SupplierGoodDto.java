package org.ng.undp.vdms.dto;

import lombok.Data;

/**
 * Author: Kingsley Eze.
 * Project: vdms
 * Date: 6/19/2017.
 */

@Data
public class SupplierGoodDto {

    private Long id;

    private String code;

    private String quality;

    private String description;

    @Override
    public String toString() {
        return "SupplierGoodDto{" +
                "code='" + code + '\'' +
                ", quality='" + quality + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
