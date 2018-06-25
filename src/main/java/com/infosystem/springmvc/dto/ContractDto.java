package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Data
@NoArgsConstructor
public class ContractDto implements Serializable {

    private int id;

    private UserDto user;

    @Size(min = 6, max = 32)
    private String phoneNumber;

    @NotNull
    private int tariffId;

    private double price;

    private Status status;

    private TariffDto tariff;

    private Set<TariffOptionDto> activeOptions;

    public TreeSet<TariffOptionDto> getActiveOptions(){
        TreeSet<TariffOptionDto> options = new TreeSet<>(Comparator.comparingInt(TariffOptionDto::getId));
        options.addAll(activeOptions);
        return options;
    }
}
