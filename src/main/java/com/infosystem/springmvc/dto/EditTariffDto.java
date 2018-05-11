package com.infosystem.springmvc.dto;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

public class EditTariffDto implements Serializable {
    @NotEmpty
    private List<TariffOptionDto> tariffOptionDtoList;
    private Integer tariffId;

    public EditTariffDto(List<TariffOptionDto> tariffOptionDtoList, Integer tariffId) {
        this.tariffOptionDtoList = tariffOptionDtoList;
        this.tariffId = tariffId;
    }

    public EditTariffDto() {
    }

    public List<TariffOptionDto> getTariffOptionDtoList() {
        return tariffOptionDtoList;
    }

    public void setTariffOptionDtoList(List<TariffOptionDto> tariffOptionDtoList) {
        this.tariffOptionDtoList = tariffOptionDtoList;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }
}
