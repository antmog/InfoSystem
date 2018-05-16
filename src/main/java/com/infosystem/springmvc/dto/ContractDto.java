package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
public class ContractDto implements Serializable {

    private int id;

    private AddUserDto user;

    @Size(min = 6, max = 32)
    private String phoneNumber;

    @NotNull
    private int tariffId;

    private double price;

    private Status status;

    private TariffDto tariff;

    private Set<TariffOptionDto> activeOptions;
}
