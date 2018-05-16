package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class ContractDto implements Serializable {
    private int userId;
    @Size(min = 6, max = 32)
    private String phoneNumber;
    @NotNull
    private int tariffId;
}
