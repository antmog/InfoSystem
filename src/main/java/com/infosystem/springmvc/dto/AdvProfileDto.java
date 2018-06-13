package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.AdvProfileTariffs;
import com.infosystem.springmvc.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AdvProfileDto {
    private Integer id;
    private String name;
    private Status status;
    private List<AdvProfileTariffDto> advProfileTariffsList;
}
