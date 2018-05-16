package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class ContractPageDto {

    private ContractDto contract;
    private List<TariffDto> tariffs;

    public ContractPageDto(ContractDto contract, List<TariffDto> tariffs) {
        this.contract = contract;
        this.tariffs = tariffs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContractPageDto that = (ContractPageDto) o;
        return Objects.equals(contract, that.contract) &&
                Objects.equals(tariffs, that.tariffs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contract, tariffs);
    }
}

