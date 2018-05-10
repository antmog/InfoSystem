package com.infosystem.springmvc.dto;

public class SwitchTariffDto {
    private Integer contractId;
    private Integer tariffId;

    public SwitchTariffDto(Integer contractId, Integer tariffId) {
        this.contractId = contractId;
        this.tariffId = tariffId;
    }

    public SwitchTariffDto() {
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }
}
