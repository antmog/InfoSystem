package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDto {

    private String responseText;

    public ResponseDto(String responseText){
        this.responseText = responseText;
    }
}
