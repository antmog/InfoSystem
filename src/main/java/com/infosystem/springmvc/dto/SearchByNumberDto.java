package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class SearchByNumberDto {
    @Pattern(regexp = "\\d*", message = "Only numbers here.")
    @Size(min = 6, max = 32)
    private String phoneNumber;
}
