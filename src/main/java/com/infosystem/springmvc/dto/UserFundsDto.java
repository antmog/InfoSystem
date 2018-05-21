package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserFundsDto {

    @NotNull
    private double amount;
    private int userId;

    public UserFundsDto(Double amount, Integer id) {
        this.amount = amount;
        this.userId = id;
    }
}
