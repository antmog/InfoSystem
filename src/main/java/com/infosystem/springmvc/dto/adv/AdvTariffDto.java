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

    public AdvTariffDto(String name, Set<AdvTariffOptionDto> availableOptions) {
        this.name = name;
        this.availableOptions = availableOptions;
    }

    public AdvTariffDto(String name, String description, String image, Double price, Set<AdvTariffOptionDto> availableOptions) {
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.availableOptions = availableOptions;
    }

    public AdvTariffDto(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public AdvTariffDto(String name) {
        this.name = name;
    }
}
