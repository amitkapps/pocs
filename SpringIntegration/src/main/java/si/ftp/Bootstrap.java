package si.ftp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.message.GenericMessage;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;

import javax.naming.NamingException;

public class Bootstrap {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-integration-config.xml");
/*
        MessageChannel channel = context.getBean("names", MessageChannel.class);
        Message<String> message = MessageBuilder.withPayload("Joe").build();
        channel.send(message);
*/
    }

}
