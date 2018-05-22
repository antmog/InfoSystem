package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
public class UserFundsDto {

    //todo pattern
    // String: @Pattern(regexp = "^[-+]?[0-9]*[.,]?[0-9]+(?:[eE][-+]?[0-9]+)?$")
    @Min(value = 10)
    private double amount;
    private int userId;

    public UserFundsDto(Double amount, Integer id) {
        this.amount = amount;
        this.userId = id;
    }
}
