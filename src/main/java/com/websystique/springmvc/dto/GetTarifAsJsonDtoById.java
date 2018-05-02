package com.websystique.springmvc.dto;

import java.util.List;

public class GetTarifAsJsonDtoById {
    private List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList;
    private Integer tariffId;

    public GetTarifAsJsonDtoById(List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList, Integer tariffId) {
        this.getOptionsAsJsonDtoList = getOptionsAsJsonDtoList;
        this.tariffId = tariffId;
    }

    public GetTarifAsJsonDtoById() {
    }

    public List<GetOptionsAsJsonDto> getGetOptionsAsJsonDtoList() {
        return getOptionsAsJsonDtoList;
    }

    public void setGetOptionsAsJsonDtoList(List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList) {
        this.getOptionsAsJsonDtoList = getOptionsAsJsonDtoList;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }
}
