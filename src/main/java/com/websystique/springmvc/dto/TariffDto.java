package com.websystique.springmvc.dto;

import java.io.Serializable;

public class TariffDto implements Serializable {
    private String name;
    private Double price;

    public TariffDto(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public TariffDto(){

    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "{\"name\":\""+name+"\",\"price\":"+price+"}";
    }
}
