package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UserFundsDto {

    @Min(value = 10)
    private double amount;
    private int userId;

    public UserFundsDto(Double amount, Integer id) {
        this.amount = amount;
        this.userId = id;
    }
}
