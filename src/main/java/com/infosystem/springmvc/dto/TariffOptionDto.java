package com.infosystem.springmvc.dto;


import com.infosystem.springmvc.model.entity.TariffOption;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
public class TariffOptionDto implements Serializable {
    @NotNull
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private Double price;
    @NotNull
    private Double costofadd;

    @Override
    public String toString() {
        return "{id: \""+id+"\",name: \"" + name + "\",price: \""+price+"\",costofadd: \""+costofadd+"\"}";
    }

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
