package amitk.poc.jms;

import org.junit.Test;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Unit test for simple App.
 */
public class JMSReceiverTest {

    @Test
    public void testReceiveMessage() {
        logger.info("Test");
    }

    private QueueReceiver queueReceiver = null;
    private QueueConnection jmsQueueConnection = null;
    private String queueType = null;
    private static org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(JMSReceiverTest.class);
    private String CONTINUE = "CONTINUE";

    private boolean initJMS(String cfName, String topicName) {
        boolean statusFlag = false;
        try {
            queueType = "JMS";

            Properties p = new Properties();
            p.put(Context.INITIAL_CONTEXT_FACTORY,
                    "org.jnp.interfaces.NamingContextFactory");
            p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
            p.put(Context.PROVIDER_URL, "jnp://10.8.7.142:1099/");


            InitialContext jndiContext = new InitialContext(p);
            QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) jndiContext.lookup(cfName);
            jmsQueueConnection = queueConnectionFactory.createQueueConnection();
            Queue queue = (Queue) jndiContext.lookup(topicName);
            QueueSession queueSession = jmsQueueConnection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            jmsQueueConnection.start();
            queueReceiver = queueSession.createReceiver(queue);
            statusFlag = true;
        } catch (Exception e) {
            logger.error("Unable to create JMS Queue Receiver.", e);
        }
        return statusFlag;
    }

    private String getJmsMessage() {
        String messageString = CONTINUE;
        try {
            Message message = queueReceiver.receiveNoWait();

            if (message == null) {
                messageString = "";
            } else if (message instanceof TextMessage) {
                messageString = ((TextMessage) message).getText();
            } else {
                logger.info("Skipping mon-text Message");
            }
        } catch (Exception e) {
            logger.error("Error getting message from JMS Queue", e);
            messageString = "";
        }
        return messageString;
    }

    public String getNextMessage() {
        String messageString = "";
        while (true) {
            String jmsMessageText = getJmsMessage();
            if (jmsMessageText.equalsIgnoreCase("")) {
                messageString = "";
                break;
            } else if (jmsMessageText.equalsIgnoreCase("CONTINUE")) {
                // stay in loop to get next message
            } else {
                messageString = jmsMessageText;
                break;
            }
        }
        return messageString;
    }


    public void close() {

        if (queueReceiver != null) {
            try {
                queueReceiver.close();
            } catch (Exception e) {
                logger.error("Unable to close JMS Queue.", e);
            }
        }
        if (jmsQueueConnection != null) {
            try {
                jmsQueueConnection.close();
            } catch (Exception e) {
                logger.error("Unable to close JMS TopicConnection.", e);
            }
        }
    }



    public static void main (String[] args){
        boolean hasMessages = true;
        List messagesList = new ArrayList();
        JMSReceiverTest chassisQueueManagement =new JMSReceiverTest();
        if (chassisQueueManagement.initJMS("jms/cf/chassis", "jms/queue/chassis")){
            logger.info("**** start collecting messages from Queue ****");
            while (hasMessages) {
                logger.info("**** before getting the message ****");
                String message = chassisQueueManagement.getNextMessage();
                if (message == null  || message.trim().equalsIgnoreCase("")){
                    logger.info("**** after getting the message from Queue **** : No Available Messages");
                    hasMessages = false;
                }else{
                    logger.info("**** after getting the message from Queue **** : '" + message + "'");
                    messagesList.add(message);
                }
            }    // END while
            chassisQueueManagement.close();
        }
    }

}
