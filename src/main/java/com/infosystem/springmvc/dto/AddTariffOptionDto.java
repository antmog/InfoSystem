package com.infosystem.springmvc.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AddTariffOptionDto {
    @Size(min = 6, max = 32)
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private Double costofadd;

    public AddTariffOptionDto(String name, Double price, Double costofadd) {
        this.name = name;
        this.price = price;
        this.costofadd = costofadd;
    }

    public AddTariffOptionDto() {
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

    public Double getCostofadd() {
        return costofadd;
    }

    public void setCostofadd(Double costofadd) {
        this.costofadd = costofadd;
    }
}
