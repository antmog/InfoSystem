package com.infosystem.springmvc.configuration;

import com.infosystem.springmvc.controller.exceptionhandlers.ExceptionDataController;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Properties;

@Configuration
public class JMSConfig {

    private Context namingContext;

    @Autowired
    private Environment environment;

    private static final Logger logger = Logger.getLogger(ExceptionDataController.class);

    @Bean
    public UserCredentialsConnectionFactoryAdapter connectionFactory() {
        try {
            String userName = System.getProperty("username", environment.getProperty("jms.username"));
            String password = System.getProperty("password", environment.getProperty("jms.password"));
            // Set up the namingContext for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, environment.getProperty("jms.initialContextFactory"));
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, environment.getProperty("jms.providerUrl")));
            env.put(Context.SECURITY_PRINCIPAL, userName);
            env.put(Context.SECURITY_CREDENTIALS, password);
            UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
            namingContext = new InitialContext(env);
            // Perform the JNDI lookups
            //class org.apache.activemq.artemis.jms.clientActiveMQJMSConnectionFactory
            String connectionFactoryString = System.getProperty("connection.factory", environment.getProperty("jms.connectionFactory"));
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
        return jmsTemplate(environment.getProperty("jms.advDestination"));
    }

    @Bean(name = "jmsSmsTemplate")
    public JmsTemplate jmsSmsTemplate() {
        return jmsTemplate(environment.getProperty("jms.smsDestination"));
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
