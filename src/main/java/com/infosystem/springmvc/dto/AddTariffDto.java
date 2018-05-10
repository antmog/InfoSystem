package com.infosystem.springmvc.dto;

import java.io.Serializable;
import java.util.List;

public class AddTariffDto implements Serializable {
    private List<TariffOptionDto> tariffOptionDtoList;
    private TariffDto tariffDto;

    public AddTariffDto(List<TariffOptionDto> tariffOptionDtoList, TariffDto tariffDto) {
        this.tariffOptionDtoList = tariffOptionDtoList;
        this.tariffDto = tariffDto;
    }

    public AddTariffDto() {
    }

    public TariffDto getTariffDto() {
        return tariffDto;
    }

    public void setTariffDto(TariffDto tariffDto) {
        this.tariffDto = tariffDto;
    }

    public List<TariffOptionDto> getTariffOptionDtoList() {
        return tariffOptionDtoList;
    }

    public void setTariffOptionDtoList(List<TariffOptionDto> tariffOptionDtoList) {
        this.tariffOptionDtoList = tariffOptionDtoList;
    }

}
