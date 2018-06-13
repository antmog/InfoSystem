package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class AdminPanelDto {
    UserDto userDto;
    List<AdvProfileDto> advProfileDtoList;
}
