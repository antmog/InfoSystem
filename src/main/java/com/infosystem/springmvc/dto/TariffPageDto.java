package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
public class TariffPageDto {

    private TariffDto tariff;
    private Set<TariffOptionDto> options;

    public TariffPageDto(TariffDto tariff, Set<TariffOptionDto> options) {
        this.tariff = tariff;
        this.options = options;
    }
}
