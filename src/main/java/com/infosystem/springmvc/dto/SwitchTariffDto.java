package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class SwitchTariffDto {
    @NotNull
    private int contractId;
    @Min(1)
    @NotNull
    private int tariffId;
}
