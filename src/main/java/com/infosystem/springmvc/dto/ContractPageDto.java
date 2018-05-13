package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.Tariff;

import java.util.List;

public class ContractPageDto {
    private Contract contract;
    private List<Tariff> tariffs;

    public ContractPageDto(Contract contract, List<Tariff> tariffs) {
        this.contract = contract;
        this.tariffs = tariffs;
    }

    public ContractPageDto() {
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    public void setTariffs(List<Tariff> tariffs) {
        this.tariffs = tariffs;
    }
}

