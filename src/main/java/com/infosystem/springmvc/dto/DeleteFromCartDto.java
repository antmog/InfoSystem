package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class DeleteFromCartDto {
    @NotNull
    private int contractId;
    @NotNull
    private int optionId;
}
