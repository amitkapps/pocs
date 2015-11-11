package poc.arch.spring.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by amitkapps on 11/10/15.
 */
@Component
public class MessageSender {

    @Autowired
    JmsTemplate jmsTemplate;

    Logger log = LoggerFactory.getLogger(MessageSender.class);

    public void sendMessage(String message){

        jmsTemplate.convertAndSend(message);
        log.info("sent message {}", message);
    }

}
