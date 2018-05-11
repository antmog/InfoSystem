package com.infosystem.springmvc.dto;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public class AddTariffDto implements Serializable {
    @Valid
    private TariffDto tariffDto;
    private List<TariffOptionDto> tariffOptionDtoList;


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
