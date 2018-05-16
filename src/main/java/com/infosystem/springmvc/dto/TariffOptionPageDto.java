package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.TariffOption;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;

@Data
@NoArgsConstructor
public class TariffOptionPageDto {

    private TariffOptionDto tariffOption;
    private Set<TariffOptionDto> options;

    public TariffOptionPageDto(TariffOptionDto tariffOption, Set<TariffOptionDto> options) {
        this.tariffOption = tariffOption;
        this.options = options;
    }
}
