package com.infosystem.springmvc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class TariffDto implements Serializable {
    private int id;
    @Size(min = 2, max = 32)
    private String name;
    @Min(1)
    @NotNull
    private double price;

    @Override
    public String toString() {
        return "{\"name\":\""+name+"\",\"price\":"+price+"}";
    }
}
