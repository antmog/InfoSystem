package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class AllUsersDto {
    private Integer pageCount;
    private Integer pageNumber;
    private List<UserDto> userDtoList;
}
