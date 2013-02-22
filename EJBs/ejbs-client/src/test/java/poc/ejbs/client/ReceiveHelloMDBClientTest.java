package poc.ejbs.client;

import org.junit.Test;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 2/20/13
 * Time: 1:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReceiveHelloMDBClientTest {     /**
 *
 */
private static final long serialVersionUID = 7105145023422143880L;
    private static Logger log = Logger.getLogger(ReceiveHelloMDBClientTest.class.getCanonicalName());


    private final String CONNECTION_FACTORY_CLUSTERED = "ClusteredConnectionFactory";
    private final String CONNECTION_FACTORY             = "ConnectionFactory";

    private String queueName = "jms.queue.hello";
    private String topicName = "jms.topic.hello";
    private String providerURL = "192.168.0.1:1099,192.168.0.2:1099";


    @Test
    public void sendQueueMessage(){
        try{

            InitialContext initialContext = getInitialContext();

            QueueConnectionFactory qcf = (QueueConnectionFactory) initialContext.lookup(CONNECTION_FACTORY_CLUSTERED);
            QueueConnection queueConn = qcf.createQueueConnection();
            Queue queue = (Queue) initialContext.lookup(queueName);
            QueueSession queueSession = queueConn.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            queueConn.start();

            QueueSender send = queueSession.createSender(queue);
            TextMessage m = queueSession.createTextMessage("HELLO");
            log.info("##### Publishing Message to a Queue: " + queueName + "#####");
            send.send(m);
            send.close();

            queueConn.stop();
            queueSession.close();
            queueConn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void sendTopicMessage(){

        try{
            InitialContext initialContext = getInitialContext();

            TopicConnectionFactory tcf = (TopicConnectionFactory)initialContext.lookup(CONNECTION_FACTORY_CLUSTERED);
            TopicConnection topicConn = tcf.createTopicConnection();
            Topic topic = (Topic) initialContext.lookup(topicName);
            TopicSession topicSession = topicConn.createTopicSession(false,TopicSession.AUTO_ACKNOWLEDGE);
            topicConn.start();

            TopicPublisher send = topicSession.createPublisher(topic);

            TextMessage m = topicSession.createTextMessage("HELLO on TOPIC");
            log.info("##### Publishing Message to a Topic: " + topicName + "#####");
            send.publish(m);
            send.close();

            topicConn.stop();
            topicSession.close();
            topicConn.close();

        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

    private InitialContext getInitialContext() throws NamingException{
        Properties jboss = new Properties();
        jboss.put("java.naming.factory.initial", "org.jnp.interfaces.NamingContextFactory");
        jboss.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");
        jboss.put("java.naming.provider.url", providerURL);
        return new InitialContext(jboss);

    }
}