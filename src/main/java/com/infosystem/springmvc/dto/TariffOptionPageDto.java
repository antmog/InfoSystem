package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Data
@NoArgsConstructor
public class TariffOptionPageDto {

    private TariffOptionDto tariffOption;
    private TreeSet<TariffOptionDto> options;

    public TariffOptionPageDto(TariffOptionDto tariffOption, Set<TariffOptionDto> options) {
        this.tariffOption = tariffOption;
        this.options = new TreeSet<>(Comparator.comparingInt(TariffOptionDto::getId));
        this.options.addAll(options);
    }
}
