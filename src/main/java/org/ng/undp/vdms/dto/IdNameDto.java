package org.ng.undp.vdms.dto;

import lombok.Data;

/**
 * Created by Saurabh on 03-01-2017.
 */
@Data
public class IdNameDto {
    private Long id;
    private String name;
    public IdNameDto(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
