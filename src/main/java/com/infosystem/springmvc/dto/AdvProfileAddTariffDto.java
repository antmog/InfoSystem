package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdvProfileAddTariffDto {
    private List<TariffDto> tariffs;
    private List<String> imgs;
    private Integer advProfileId;
}
