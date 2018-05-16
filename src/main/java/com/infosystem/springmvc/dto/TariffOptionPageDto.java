package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.TariffOption;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class TariffOptionPageDto {
    private TariffOption tariffOption;
    private List<TariffOption> options;

    public TariffOptionPageDto(TariffOption tariffOption, List<TariffOption> options) {
        this.tariffOption = tariffOption;
        this.options = options;
    }
}
