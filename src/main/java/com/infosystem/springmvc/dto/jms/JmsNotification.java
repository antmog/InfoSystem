package com.infosystem.springmvc.dto.jms;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JmsNotification {

    private String type;
    private String item;
    private String itemName;
    private String description;

}
