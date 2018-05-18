package com.infosystem.springmvc.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Amount {
    private Double amount;

    public Amount(){
        amount = 0.0;
    }

    public void add(Double value){
        amount += value;
    }
}
