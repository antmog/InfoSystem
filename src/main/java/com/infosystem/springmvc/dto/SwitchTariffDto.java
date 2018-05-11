package com.infosystem.springmvc.dto;

import javax.validation.constraints.NotNull;

public class SwitchTariffDto {
    @NotNull
    private Integer contractId;
    @NotNull
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
