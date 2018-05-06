package com.websystique.springmvc.dto;

public class SwitchTariffDto {
    private Integer ContractId;
    private Integer tariffId;

    public SwitchTariffDto(Integer contractId, Integer tariffId) {
        ContractId = contractId;
        this.tariffId = tariffId;
    }

    public SwitchTariffDto() {
    }

    public Integer getContractId() {
        return ContractId;
    }

    public void setContractId(Integer contractId) {
        ContractId = contractId;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }
}
