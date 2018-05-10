package com.infosystem.springmvc.dto;

import java.io.Serializable;
import java.util.List;

public class GetTarifAsJsonDto implements Serializable {
    private List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList;
    private TariffDto tariffDto;

    public GetTarifAsJsonDto(List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList, TariffDto tariffDto) {
        this.getOptionsAsJsonDtoList = getOptionsAsJsonDtoList;
        this.tariffDto = tariffDto;
    }

    public GetTarifAsJsonDto() {
    }

    public TariffDto getTariffDto() {
        return tariffDto;
    }

    public void setTariffDto(TariffDto tariffDto) {
        this.tariffDto = tariffDto;
    }

    public List<GetOptionsAsJsonDto> getGetOptionsAsJsonDtoList() {
        return getOptionsAsJsonDtoList;
    }

    public void setGetOptionsAsJsonDtoList(List<GetOptionsAsJsonDto> getOptionsAsJsonDtosList) {
        this.getOptionsAsJsonDtoList = getOptionsAsJsonDtosList;
    }

}
