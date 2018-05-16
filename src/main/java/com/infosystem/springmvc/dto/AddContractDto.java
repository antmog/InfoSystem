package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class AddContractDto implements Serializable {
    @Valid
    private ContractDto contractDto;
    private List<TariffOptionDto> tariffOptionDtoList;
}
