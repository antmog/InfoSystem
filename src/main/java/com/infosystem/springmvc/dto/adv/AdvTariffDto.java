package com.infosystem.springmvc.dto.adv;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
public class AdvTariffDto {

    private String name;
    private String description;
    private String image;
    private Double price;
    private Set<AdvTariffOptionDto> availableOptions;
}
