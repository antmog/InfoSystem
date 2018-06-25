package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class TariffOptionDto implements Serializable {

    @NotNull
    private int id;
    @NotNull
    private String name;

    private String description;
    @NotNull
    private double price;
    @NotNull
    private double costofadd;

    @Override
    public String toString() {
        return "{id: \"" + id + "\",name: \"" + name + "\",price: \"" + price + "\",costofadd: \"" + costofadd + "\"}";
    }

    private Set<TariffOptionDto> relatedTariffOptions;

    private Set<TariffOptionDto> excludingTariffOptions;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TariffOptionDto that = (TariffOptionDto) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(price, that.price) &&
                Objects.equals(costofadd, that.costofadd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, costofadd);
    }
}
