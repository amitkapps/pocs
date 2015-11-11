package poc.arch.spring.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by amitkapps on 11/10/15.
 */
public class MessageReceiver implements MessageListener{
    Logger logger = LoggerFactory.getLogger(MessageReceiver.class);

    private long counter = 0;
    public MessageReceiver(){
        logger.info("COUNTER RESET TO 1");
    }

    public void receiveMessage(String message){
        if(Long.valueOf(message) == 1){
            logger.info("~~~~~~~~~~ COUNTER RESET");
            counter = 0;
        }
        if(counter % 5000 == 0)
            logger.info("################ RECEIVED MESSAGE {}:{}", ++counter, message);
        else
            logger.info("RECEIVED MESSAGE {}: {}",++counter, message);

    }

    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage){
            try {
                receiveMessage(((TextMessage)message).getText());
            } catch (JMSException e) {
                logger.error("Could not extract text message", e);
            }
        }
        else {
            logger.warn("Unexpected message received {}", message);
        }
    }
}
