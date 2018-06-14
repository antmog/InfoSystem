package com.infosystem.springmvc.dto.adv;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdvTariffOptionDto {
    private int id;
    private String name;
    private double price;
    private double costofadd;
}
