package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class AddContractDto implements Serializable {

    @Min(1)
    @NotNull
    private int userId;
    @Pattern(regexp = "\\d*", message = "Only numbers here.")
    @Size(min = 6, max = 32)
    private String phoneNumber;
    @NotNull
    @Min(value = 1)
    private int tariffId;
    private List<TariffOptionDto> tariffOptionDtoList;
}
