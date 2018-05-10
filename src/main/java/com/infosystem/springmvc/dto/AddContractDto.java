package com.infosystem.springmvc.dto;

import java.io.Serializable;
import java.util.List;


public class AddContractDto implements Serializable {
    private ContractDto contractDto;
    private List<TariffOptionDto> tariffOptionDtoList;

    public AddContractDto(ContractDto contractDto, List<TariffOptionDto> tariffOptionDtoList) {
        this.contractDto = contractDto;
        this.tariffOptionDtoList = tariffOptionDtoList;
    }

    public AddContractDto() {
    }

    public ContractDto getContractDto() {
        return contractDto;
    }

    public void setContractDto(ContractDto contractDto) {
        this.contractDto = contractDto;
    }

    public List<TariffOptionDto> getTariffOptionDtoList() {
        return tariffOptionDtoList;
    }

    public void setTariffOptionDtoList(List<TariffOptionDto> tariffOptionDtoList) {
        this.tariffOptionDtoList = tariffOptionDtoList;
    }
}
