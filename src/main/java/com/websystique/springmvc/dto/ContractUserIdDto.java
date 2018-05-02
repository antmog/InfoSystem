package com.websystique.springmvc.dto;

import com.websystique.springmvc.model.Contract;
import lombok.Data;

@Data
public class ContractUserIdDto {
    private Integer user_id;
    private String phoneNumber;

    public ContractUserIdDto(Integer user_id, String phoneNumber) {
        this.user_id = user_id;
        this.phoneNumber = phoneNumber;
    }
    public ContractUserIdDto(){

    }

}
