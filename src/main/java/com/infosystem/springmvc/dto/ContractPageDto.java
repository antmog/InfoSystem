package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.Contract;
import com.infosystem.springmvc.model.entity.Tariff;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ContractPageDto {
    private Contract contract;
    private List<Tariff> tariffs;

    public ContractPageDto(Contract contract, List<Tariff> tariffs) {
        this.contract = contract;
        this.tariffs = tariffs;
    }
}

