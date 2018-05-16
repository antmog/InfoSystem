package com.infosystem.springmvc.dto.editUserDto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class EditPassportDto {
    @NotNull
    private Integer userId;

    @Pattern(regexp = "\\d*")
    @Size(min = 6, max = 9)
    private String value;
}
