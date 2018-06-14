package com.infosystem.springmvc.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infosystem.springmvc.dto.adv.AdvTariffDto;
import com.infosystem.springmvc.dto.adv.AdvTariffOptionDto;
import com.infosystem.springmvc.dto.jms.JmsNotification;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.util.ToAdvertismentJmsJsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MessageSender {
    private final JmsTemplate jmsTemplate;
    private final ToAdvertismentJmsJsonMapper toAdvertismentJmsJsonMapper;

    @Autowired
    public MessageSender(JmsTemplate jmsTemplate, ToAdvertismentJmsJsonMapper toAdvertismentJmsJsonMapper) {
        this.jmsTemplate = jmsTemplate;
        this.toAdvertismentJmsJsonMapper = toAdvertismentJmsJsonMapper;
    }

    private void sendMessage(String string) {
        jmsTemplate.send(session -> session.createTextMessage(string));
    }

    private void parseBeforeSend(JmsNotification jmsNotification) {
        try {
            sendMessage(toAdvertismentJmsJsonMapper.mapToAdvertismentJmsJson(jmsNotification));
        } catch (JsonProcessingException e) {
            //todo normal exception handling
            //todo log mb
            e.printStackTrace();
        }
    }

    public void initiateSend(String type, String tariffName, List<AdvTariffOptionDto> optionDtoSet) {
        AdvTariffDto advTariffDto = new AdvTariffDto(tariffName, new HashSet<>(optionDtoSet));
        JmsNotification jmsNotification = new JmsNotification(type, advTariffDto);
        parseBeforeSend(jmsNotification);
    }

    public void initiateSend(String type, AdvTariffDto advTariffDto) {
        JmsNotification jmsNotification = new JmsNotification(type, advTariffDto);
        parseBeforeSend(jmsNotification);
    }

    public void initiateSend(String type, String tariffName, String img) {
        AdvTariffDto advTariffDto = new AdvTariffDto(tariffName, img);
        JmsNotification jmsNotification = new JmsNotification(type, advTariffDto);
        parseBeforeSend(jmsNotification);
    }

    public void initiateSend(String type, String tariffName) {
        AdvTariffDto advTariffDto = new AdvTariffDto(tariffName);
        JmsNotification jmsNotification = new JmsNotification(type, advTariffDto);
        parseBeforeSend(jmsNotification);
    }

    public void initiateSend(String type) {
        JmsNotification jmsNotification = new JmsNotification(type);
        parseBeforeSend(jmsNotification);
    }

}