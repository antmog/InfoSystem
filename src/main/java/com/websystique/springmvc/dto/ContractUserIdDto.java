package com.websystique.springmvc.dto;

import com.websystique.springmvc.model.Contract;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


public class ContractUserIdDto implements Serializable {
    private ContractDto contractDto;
    private List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList;

    public ContractUserIdDto(ContractDto contractDto, List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList) {
        this.contractDto = contractDto;
        this.getOptionsAsJsonDtoList = getOptionsAsJsonDtoList;
    }

    public ContractUserIdDto() {
    }

    public ContractDto getContractDto() {
        return contractDto;
    }

    public void setContractDto(ContractDto contractDto) {
        this.contractDto = contractDto;
    }

    public List<GetOptionsAsJsonDto> getGetOptionsAsJsonDtoList() {
        return getOptionsAsJsonDtoList;
    }

    public void setGetOptionsAsJsonDtoList(List<GetOptionsAsJsonDto> getOptionsAsJsonDtoList) {
        this.getOptionsAsJsonDtoList = getOptionsAsJsonDtoList;
    }
}
