package com.infosystem.springmvc.dto;

import javax.validation.constraints.NotNull;

public class DeleteFromCartDto {
    @NotNull
    private Integer contractId;
    @NotNull
    private Integer optionId;

    public DeleteFromCartDto(Integer contractId, Integer optionId) {
        this.contractId = contractId;
        this.optionId = optionId;
    }

    public DeleteFromCartDto() {
    }

    public Integer getContractId() {
        return contractId;
    }

    public void setContractId(Integer contractId) {
        this.contractId = contractId;
    }

    public Integer getOptionId() {
        return optionId;
    }

    public void setOptionId(Integer optionId) {
        this.optionId = optionId;
    }
}
