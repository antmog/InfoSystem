package com.infosystem.springmvc.dto.editUserDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class EditAddressDto {
    @NotNull
    private Integer userId;

    @Size(min = 6, max = 32)
    private String value;
}
