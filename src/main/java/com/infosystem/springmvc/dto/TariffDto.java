package com.infosystem.springmvc.dto;

import com.infosystem.springmvc.model.entity.TariffOption;
import com.infosystem.springmvc.model.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

@Data
@NoArgsConstructor
public class TariffDto implements Serializable {

    private int id;

    @Size(min = 2, max = 32)
    private String name;

    @Min(1)
    @NotNull
    private double price;

    private Set<TariffOptionDto> availableOptions;

    public TreeSet<TariffOptionDto> getAvailableOptions(){
        TreeSet<TariffOptionDto> options = new TreeSet<>(Comparator.comparingInt(TariffOptionDto::getId));
        options.addAll(availableOptions);
        return options;
    }

    private Status status;

    @Size(min = 2, max = 32)
    private String description;

    @Override
    public String toString() {
        return "{\"name\":\""+name+"\",\"price\":"+price+"}";
    }
}
