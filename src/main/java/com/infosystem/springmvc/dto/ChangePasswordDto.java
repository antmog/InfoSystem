package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class ChangePasswordDto {

    private int userId;
    @Size(min = 6, max = 32)
    private String password;
    @Size(min = 6, max = 32)
    private String newPassword;
}
