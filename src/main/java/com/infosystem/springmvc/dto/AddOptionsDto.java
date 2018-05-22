package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AddOptionsDto {
    //todo pattern
    @NotNull
    private int userId;
}
