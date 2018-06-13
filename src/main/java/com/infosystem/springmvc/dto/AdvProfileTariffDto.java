package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class AdvProfileTariffDto {
    @Min(1)
    private int advProfileId;
    @Min(1)
    private int tariffId;
    private String tariffName;
    @NotNull
    private String img;
    private List<String> imgs;
}
