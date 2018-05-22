package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class AddTariffOptionDto {
    @Size(min = 6, max = 32)
    private String name;
    //todo pattern
    @NotNull
    private double price;
    //todo pattern
    @NotNull
    private double costofadd;
}
