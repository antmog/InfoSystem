package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Data
@NoArgsConstructor
public class TariffPageDto {

    private TariffDto tariff;
    private TreeSet<TariffOptionDto> options;

    public TariffPageDto(TariffDto tariff, Set<TariffOptionDto> options) {
        this.tariff = tariff;
        this.options = new TreeSet<>(Comparator.comparingInt(TariffOptionDto::getId));
        this.options.addAll(options);
    }
}
