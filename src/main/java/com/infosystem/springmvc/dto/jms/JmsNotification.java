package com.infosystem.springmvc.dto.jms;

import com.infosystem.springmvc.dto.adv.AdvTariffDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JmsNotification {

    private String type;
    private AdvTariffDto advTariffDto;

    public JmsNotification(String type, AdvTariffDto advTariffDto) {
        this.type = type;
        this.advTariffDto = advTariffDto;
    }

    public JmsNotification(String type) {
        this.type = type;
    }
}
