package com.infosystem.springmvc.dto;

import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;


public class ContractDto implements Serializable {
    private Integer userId;
    @Size(min = 6, max = 32)
    private String phoneNumber;
    @NotNull
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
