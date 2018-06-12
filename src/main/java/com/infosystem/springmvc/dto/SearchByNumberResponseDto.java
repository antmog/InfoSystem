package com.infosystem.springmvc.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchByNumberResponseDto {

    private Integer userId;

    public SearchByNumberResponseDto(Integer userId) {
        this.userId = userId;
    }
}
