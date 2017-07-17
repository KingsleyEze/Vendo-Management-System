package org.ng.undp.vdms.dto;

import lombok.Data;

import javax.validation.constraints.Size;

/**
 * Created by emmanuel on 3/2/17.
 */
@Data
public class ChangePasswordDto {


    /*@Size(min = 5, message = "Old Password cannot be less than 5 Characters")
    private String oldPassword;*/
    @Size(min = 5, message = "Password cannot be less than 5 Characters")
    private String password;
    @Size(min = 5, message = "Password confirmation cannot be less than 5 Characters")
    private String confirmPassword;
}
