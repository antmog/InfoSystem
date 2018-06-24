package com.infosystem.springmvc.configuration;

import com.infosystem.springmvc.controller.exceptionhandlers.ExceptionDataController;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

@Configuration
public class JMSConfig {
    private Context namingContext;
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_ADV_DESTINATION = "jms/queue/infoSystem";
    private static final String DEFAULT_SMS_DESTINATION = "jms/queue/infoSystemSms";
    private static final String DEFAULT_USERNAME = "antmog";
    private static final String DEFAULT_PASSWORD = "password";
    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";
    //private static final String PROVIDER_URL = "http-remoting://advapp-service:8080";
    private static final Logger logger = Logger.getLogger(ExceptionDataController.class);

    @Bean
    public UserCredentialsConnectionFactoryAdapter connectionFactory() {
        try {
            String userName = System.getProperty("username", DEFAULT_USERNAME);
            String password = System.getProperty("password", DEFAULT_PASSWORD);
            // Set up the namingContext for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            env.put(Context.SECURITY_PRINCIPAL, userName);
            env.put(Context.SECURITY_CREDENTIALS, password);
            UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
            namingContext = new InitialContext(env);
            // Perform the JNDI lookups
            //class org.apache.activemq.artemis.jms.clientActiveMQJMSConnectionFactory
            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
            userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(connectionFactory);
            userCredentialsConnectionFactoryAdapter.setUsername(userName);
            userCredentialsConnectionFactoryAdapter.setPassword(password);
            return userCredentialsConnectionFactoryAdapter;
        } catch (Exception e) {
            logger.error("Cant create connection to JMS server.", e);
        }
        return null;
    }

    @Bean(name = "jmsAdvTemplate")
    public JmsTemplate jmsAdvTemplate() {
        return jmsTemplate(DEFAULT_ADV_DESTINATION);
    }

    @Bean(name = "jmsSmsTemplate")
    public JmsTemplate jmsSmsTemplate() {
        return jmsTemplate(DEFAULT_SMS_DESTINATION);
    }

    private JmsTemplate jmsTemplate(String jmsDestination){
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        try {
            String destinationString = System.getProperty("destination", jmsDestination);
            Destination destination = (Destination) namingContext.lookup(destinationString);
            template.setDefaultDestination(destination);
        } catch (Exception e) {
            logger.error("Cant create connection to JMS server: cant find destination for JmsTemplate.", e);
        }
        return template;
    }
}
