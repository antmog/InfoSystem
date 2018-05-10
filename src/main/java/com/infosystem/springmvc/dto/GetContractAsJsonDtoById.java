package com.infosystem.springmvc.dto;

import java.util.List;

public class GetContractAsJsonDtoById {
    private List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList;
    private Integer contractId;

    public GetContractAsJsonDtoById(List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList, Integer contractId) {
        this.getOptionsAsJsonDtoList = getOptionsAsJsonDtoList;
        this.contractId = contractId;
    }

    public GetContractAsJsonDtoById() {
    }

    public List<GetOptionsAsJsonDto> getGetOptionsAsJsonDtoList() {
        return getOptionsAsJsonDtoList;
    }

    public void setGetOptionsAsJsonDtoList(List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList) {
        this.getOptionsAsJsonDtoList = getOptionsAsJsonDtoList;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }
}
