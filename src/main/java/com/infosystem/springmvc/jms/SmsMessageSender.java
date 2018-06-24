package com.infosystem.springmvc.jms;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SmsMessageSender {

    private static final Logger logger = Logger.getLogger(SmsMessageSender.class);

    private final JmsTemplate jmsTemplate;

    @Autowired
    public SmsMessageSender(JmsTemplate jmsSmsTemplate){
        this.jmsTemplate = jmsSmsTemplate;
    }

    public void sendMessage(String string) {
        jmsTemplate.send(session -> session.createTextMessage(string));
        logger.info("Jms msg sent to " + jmsTemplate.getDefaultDestination() + ".");
    }
}
