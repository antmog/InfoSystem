package com.infosystem.springmvc.dto;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class EditContractDto {
    @NotEmpty
    private List<TariffOptionDto> tariffOptionDtoList;
    private Integer contractId;

    public EditContractDto(List<TariffOptionDto> tariffOptionDtoList, Integer contractId) {
        this.tariffOptionDtoList = tariffOptionDtoList;
        this.contractId = contractId;
    }

    public EditContractDto() {
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
