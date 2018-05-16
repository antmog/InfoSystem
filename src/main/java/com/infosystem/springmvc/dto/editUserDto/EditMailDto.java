package com.infosystem.springmvc.dto.editUserDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class EditMailDto {
    @NotNull
    private Integer userId;

    @Email
    @Size(min = 6, max = 32)
    private String value;
}
