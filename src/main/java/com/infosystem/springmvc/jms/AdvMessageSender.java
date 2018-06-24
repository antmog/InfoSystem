package com.infosystem.springmvc.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infosystem.springmvc.dto.adv.AdvTariffDto;
import com.infosystem.springmvc.dto.adv.AdvTariffOptionDto;
import com.infosystem.springmvc.dto.jms.JmsNotification;
import com.infosystem.springmvc.exception.ValidationException;
import com.infosystem.springmvc.util.ToAdvertismentJmsJsonMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
public class AdvMessageSender {

    private static final Logger logger = Logger.getLogger(AdvMessageSender.class);
    private final JmsTemplate jmsTemplate;
    private final ToAdvertismentJmsJsonMapper toAdvertismentJmsJsonMapper;

    @Autowired
    public AdvMessageSender(JmsTemplate jmsAdvTemplate, ToAdvertismentJmsJsonMapper toAdvertismentJmsJsonMapper) {
        this.jmsTemplate = jmsAdvTemplate;
        this.toAdvertismentJmsJsonMapper = toAdvertismentJmsJsonMapper;
    }

    private void sendMessage(String string) {
        jmsTemplate.send(session -> session.createTextMessage(string));
        logger.info("Jms msg sent to " + jmsTemplate.getDefaultDestination() + ".");
    }

    private void parseBeforeSend(JmsNotification jmsNotification) throws ValidationException {
        try {
            sendMessage(toAdvertismentJmsJsonMapper.mapToAdvertismentJmsJson(jmsNotification));
        } catch (JsonProcessingException e) {
            throw new ValidationException("Cant parse to json before send JMS message.");
        }
    }

    void initiateSendEditTariff(String type, String tariffName, List<AdvTariffOptionDto> optionDtoSet) throws ValidationException {
        AdvTariffDto advTariffDto = new AdvTariffDto(tariffName, new HashSet<>(optionDtoSet));
        JmsNotification jmsNotification = new JmsNotification(type, advTariffDto);
        parseBeforeSend(jmsNotification);
    }

    void initiateSendNewTariff(AdvTariffDto advTariffDto) throws ValidationException {
        JmsNotification jmsNotification = new JmsNotification("newTariff", advTariffDto);
        parseBeforeSend(jmsNotification);
    }

    void initiateSendChangeImage(String tariffName, String img) throws ValidationException {
        AdvTariffDto advTariffDto = new AdvTariffDto(tariffName, img);
        JmsNotification jmsNotification = new JmsNotification("changeImage", advTariffDto);
        parseBeforeSend(jmsNotification);
    }

    void initiateSendDelTariff(String tariffName) throws ValidationException {
        AdvTariffDto advTariffDto = new AdvTariffDto(tariffName);
        JmsNotification jmsNotification = new JmsNotification("delTariff", advTariffDto);
        parseBeforeSend(jmsNotification);
    }

    void initiateSend() throws ValidationException {
        JmsNotification jmsNotification = new JmsNotification("changeProfile");
        parseBeforeSend(jmsNotification);
    }

}