package MQJavaClient;

import com.ibm.jms.JMSTextMessage;
import com.ibm.mq.*;
import com.ibm.mq.jms.*;
import com.ibm.mq.jms.MQQueue;
import org.junit.*;

import javax.jms.JMSException;
import javax.jms.Session;
import java.io.IOException;

/**
 * Created by amitkapps on 12/29/15.
 */
public class PutMQMessagesJmsApi {

    private MQQueueConnection connection;
    private MQQueueSession session;
    private MQQueueSender sender;
    private MQQueue queue;
    private MQQueueManager queueManager;

    @Before
    public void init() throws JMSException, MQException {
        getMQQueueSession();
        getMQQueueSender(session);
        System.out.println("MQ Resources initialized");
    }

    @After
    public void destroy() throws JMSException, MQException {
        sender.close();
        session.close();
        connection.close();

        System.out.println("MQ Resources closed");
    }

    @Test
    public void test_putMessages() throws JMSException, InterruptedException, IOException {

        System.out.println("Putting messages to MQ");
        JMSTextMessage message = (JMSTextMessage) session.createTextMessage(String.valueOf(""));


        for(int i=1; i<=1; i++){
            message.setText(String.valueOf(i));
            sender.send(message);
            System.out.println("Sent message: " + i);
//            Thread.sleep(100);
        }
    }

    private MQQueueSession getMQQueueSession() throws JMSException, MQException {

        MQQueueConnectionFactory cf = new MQQueueConnectionFactory();

        // Config
        cf.setHostName("mqpoc.dev.matson.com");
        cf.setPort(1421);
        cf.setTransportType(JMSC.MQJMS_TP_CLIENT_MQ_TCPIP);
        cf.setQueueManager("MQMI_POC");
        cf.setChannel("SYSTEM.ADMIN.SVRCONN");


        connection = (MQQueueConnection) cf.createQueueConnection();
        connection.start();
        session = (MQQueueSession) connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
        return session;

    }

    private MQQueueSender getMQQueueSender(MQQueueSession session) throws JMSException {
        queue = (MQQueue) session.createQueue("queue:///TEST.QL");
        sender = (MQQueueSender) session.createSender(queue);
        return sender;

    }
}
