package arch.poc.mqrar.jb6;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jndi.JndiObjectFactoryBean;
//import com.ibm.mqjms.*;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

/**
 *
 * @author DTownsend
 *
 *         Setup Object
 *
 */
@EnableAutoConfiguration
@EnableJms
@Configuration
@ComponentScan(basePackages = "arch.poc")
public class Application {

    Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean // Strictly speaking this bean is not necessary as boot creates a default
    JmsListenerContainerFactory<?> jmsListenerContainerFactory(ConnectionFactory connectionFactory) {

        SimpleJmsListenerContainerFactory factory = new SimpleJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory());
        return factory;
    }

    @Bean(name="connectionFactory")
    ConnectionFactory connectionFactory(){
        logger.info("instantiating connectionFactory bean");
        JndiObjectFactoryBean jndiObjectFactoryBean = new JndiObjectFactoryBean();
        jndiObjectFactoryBean.setLookupOnStartup(true);
        jndiObjectFactoryBean.setResourceRef(false);
        jndiObjectFactoryBean.setJndiName("java:/jboss/jms/wmq/connectionFactory");
        ConnectionFactory connectionFactory = (ConnectionFactory)jndiObjectFactoryBean.getObject();
        logger.info("Got connection factory {}", connectionFactory);
        return connectionFactory;
    }

    @Bean(name = "jmsTemplate")
    public JmsTemplate jmsTemplate() {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory());
        return jmsTemplate;
    }


/*
    @Bean
    public ConnectionFactory connectionFactory() {
        MQXAConnectionFactory factory = new MQXAConnectionFactory();
        try {
            factory.setHostName("10.101.20.187");
            factory.setPort("1421");
            factory.setQueueManager("MQMI_POC");
            factory.setChannel("TEST.SVRCON");
            factory.setTransportType("MQ.CLIENT");
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

        return factory;
    }
*/
}