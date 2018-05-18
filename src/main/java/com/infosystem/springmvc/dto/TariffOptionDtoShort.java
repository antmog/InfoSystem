package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TariffOptionDtoShort {

    private int id;

    private String name;

    private double price;

    private double costofadd;

}
