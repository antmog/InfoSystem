package com.infosystem.springmvc.dto;

import java.util.List;

public class ContractOptionsDto {
    private List<TariffOptionDto> tariffOptionDtoList;
    private Integer contractId;

    public ContractOptionsDto(List<TariffOptionDto> tariffOptionDtoList, Integer contractId) {
        this.tariffOptionDtoList = tariffOptionDtoList;
        this.contractId = contractId;
    }

    public ContractOptionsDto() {
    }

    public List<TariffOptionDto> getTariffOptionDtoList() {
        return tariffOptionDtoList;
    }

    public void setTariffOptionDtoList(List<TariffOptionDto> tariffOptionDtoList) {
        this.tariffOptionDtoList = tariffOptionDtoList;
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }
}
