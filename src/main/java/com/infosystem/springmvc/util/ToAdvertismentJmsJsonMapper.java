package com.infosystem.springmvc.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infosystem.springmvc.dto.jms.JmsNotification;
import org.springframework.stereotype.Component;

@Component
public class ToAdvertismentJmsJsonMapper {
    ObjectMapper jacksonMapper = new ObjectMapper();

    public String mapToAdvertismentJmsJson(JmsNotification jmsNotification) throws JsonProcessingException {
        return jacksonMapper.writeValueAsString(jmsNotification);
    }
}
