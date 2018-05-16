package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AddTariffOptionDto {
    @Size(min = 6, max = 32)
    private String name;
    @NotNull
    private double price;
    @NotNull
    private double costofadd;
}
