package com.infosystem.springmvc.dto.adv;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class AdvInitialDataDto {
    private List<AdvTariffDto> advTariffDtoList;
}
