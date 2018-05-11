package com.infosystem.springmvc.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class TariffDto implements Serializable {
    @Size(min = 2, max = 32)
    private String name;
    @Min(1)
    @NotNull
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
