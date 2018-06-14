package com.infosystem.springmvc.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infosystem.springmvc.dto.jms.JmsNotification;
import com.infosystem.springmvc.util.ToAdvertismentJmsJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JmsDataMapper {

    @Autowired
    MessageSender messageSender;

    @Autowired
    ToAdvertismentJmsJsonMapper toAdvertismentJmsJsonMapper;

    private void initiateSend(){
        //todo del
        JmsNotification jmsNotification = new JmsNotification();
        jmsNotification.setType("Alert");
        jmsNotification.setItem("Tariff");
        jmsNotification.setItemName("Super Hot");
        jmsNotification.setDescription("Description blah blahblah blah blah");
        try {
            messageSender.sendMessage(toAdvertismentJmsJsonMapper.mapToAdvertismentJmsJson(jmsNotification));
        } catch (JsonProcessingException e) {
            //todo normal exception handling
            e.printStackTrace();
        }
    }
}


