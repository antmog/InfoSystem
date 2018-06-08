package com.infosystem.springmvc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsTemplate;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import java.util.Arrays;
import java.util.Properties;

@Configuration
public class JMSConfig {
    private Context namingContext;
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    //private static final String DEFAULT_DESTINATION = "jms/queue/test";
    private static final String DEFAULT_DESTINATION = "jms/queue/infoSystem";
    private static final String DEFAULT_USERNAME = "antmog";
    private static final String DEFAULT_PASSWORD = "qwrp124";
    private static final String INITIAL_CONTEXT_FACTORY = "org.wildfly.naming.client.WildFlyInitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:8080";
    private static final String BROKER_URL = "tcp://127.0.0.1:8080";
    private static final String DEFAULT_BROKER_URL = "tcp://localhost:61616";
    @Bean
    public UserCredentialsConnectionFactoryAdapter connectionFactory() throws NamingException {
        String userName = System.getProperty("username", DEFAULT_USERNAME);
        String password = System.getProperty("password", DEFAULT_PASSWORD);
        // Set up the namingContext for the JNDI lookup
        final Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
        env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
        env.put(Context.SECURITY_PRINCIPAL, userName);
        env.put(Context.SECURITY_CREDENTIALS, password);
        namingContext = new InitialContext(env);
        // Perform the JNDI lookups
        //class org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory
        String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
        ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString) ;


//        ConnectionFactory connectionFactory = wrapper.wrapConnectionFactory(
//                new ArtemisConnectionFactoryFactory(beanFactory, properties)
//                        .createConnectionFactory(ActiveMQXAConnectionFactory.class))
//        ActiveMQJMSConnectionFactory connectionFactory = new ActiveMQJMSConnectionFactory();
//        connectionFactory.ur(BROKER_URL);
        UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
        userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(connectionFactory);
        userCredentialsConnectionFactoryAdapter.setUsername(userName);
        userCredentialsConnectionFactoryAdapter.setPassword(password);
        return userCredentialsConnectionFactoryAdapter;
    }

    @Bean
    public JmsTemplate jmsTemplate() throws NamingException {
        JmsTemplate template = new JmsTemplate();
        template.setConnectionFactory(connectionFactory());
        String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
        Destination destination = (Destination) namingContext.lookup(destinationString);
        template.setDefaultDestination(destination);
        return template;
    }
}
