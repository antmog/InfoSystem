package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AdminFundsDto {

    @NotNull
    private double amount;
    private int userId;

    public AdminFundsDto(Double amount, Integer id) {
        this.amount = amount;
        this.userId = id;
    }
}
