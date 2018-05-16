package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.Tariff;
import com.infosystem.springmvc.model.entity.TariffOption;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TariffPageDto {
    private Tariff tariff;
    private List<TariffOption> options;

    public TariffPageDto(Tariff tariff, List<TariffOption> options) {
        this.tariff = tariff;
        this.options = options;
    }
}
