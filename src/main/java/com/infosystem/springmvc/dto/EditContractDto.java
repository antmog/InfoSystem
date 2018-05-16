package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class EditContractDto {
    @NotEmpty
    private List<TariffOptionDto> tariffOptionDtoList;
    @NotNull
    private Integer contractId;
}
