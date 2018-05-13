package com.infosystem.springmvc.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class TariffOptionRulesDto {
    @Enumerated(EnumType.STRING)
    @NotNull
    private String rule;
    private Integer tariffOptionId;
    @NotEmpty
    private List<TariffOptionDto> tariffOptionDtoList;

    public TariffOptionRulesDto() {
    }

    public TariffOptionRulesDto(@NotNull String rule, Integer tariffOptionId, @NotEmpty List<TariffOptionDto> tariffOptionDtoList) {
        this.rule = rule;
        this.tariffOptionId = tariffOptionId;
        this.tariffOptionDtoList = tariffOptionDtoList;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Integer getTariffOptionId() {
        return tariffOptionId;
    }

    public void setTariffOptionId(Integer tariffOptionId) {
        this.tariffOptionId = tariffOptionId;
    }

    public List<TariffOptionDto> getTariffOptionDtoList() {
        return tariffOptionDtoList;
    }

    public void setTariffOptionDtoList(List<TariffOptionDto> tariffOptionDtoList) {
        this.tariffOptionDtoList = tariffOptionDtoList;
    }
}
