package com.infosystem.springmvc.dto;


import java.io.Serializable;


public class TariffOptionDto implements Serializable {
    private Integer id;
    private String name;
    private Double price;
    private Double costofadd;

    public TariffOptionDto(Integer id, String name, Double price, Double costOfAdd) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.costofadd = costOfAdd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Double getCostOfAdd() {
        return costofadd;
    }

    public void setCostOfAdd(Double costOfAdd) {
        this.costofadd = costOfAdd;
    }

    public TariffOptionDto(){

    }

    @Override
    public String toString() {
        return "{id: \""+id+"\",name: \"" + name + "\",price: \""+price+"\",costofadd: \""+costofadd+"\"}";
    }
}
