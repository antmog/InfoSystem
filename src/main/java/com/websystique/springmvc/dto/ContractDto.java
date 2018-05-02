package com.websystique.springmvc.dto;

import lombok.Data;


public class ContractDto {
    private Integer userId;
    private String phoneNumber;
    private Integer tariffId;

    public ContractDto(Integer userId, String phoneNumber,Integer tariffId) {
        this.userId = userId;
        this.phoneNumber = phoneNumber;
        this.tariffId = tariffId;
    }

    public ContractDto() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getTariffId() {
        return tariffId;
    }

    public void setTariffId(Integer tariffId) {
        this.tariffId = tariffId;
    }
}
