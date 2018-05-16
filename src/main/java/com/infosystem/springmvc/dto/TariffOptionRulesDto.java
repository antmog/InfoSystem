package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class TariffOptionRulesDto {

    private int tariffOptionId;

    @Enumerated(EnumType.STRING)
    @NotNull
    private String rule;

    @NotEmpty
    private List<TariffOptionDto> tariffOptionDtoList;
}
