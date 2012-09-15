package amitk.poc.jms;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Properties;

/**
 * Unit test for simple App.
 */
public class JMSMessageSenderTest {

    private static Logger logger = LoggerFactory.getLogger(JMSMessageSenderTest.class);

    @Test
    public void testReceiveMessage() {
        logger.info("Test");
    }


    QueueConnection conn;
    QueueSession session;
    Queue que;

    public static class ExListener
            implements MessageListener {
        private static Logger logger = LoggerFactory.getLogger(ExListener.class);

        public void onMessage(Message msg) {
            TextMessage tm = (TextMessage) msg;
            try {
                logger.info("onMessage, recv text=" + tm.getText());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    public void setupPTP()
            throws JMSException,
            NamingException {

        Properties p = new Properties();
        p.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        //p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, "t3://10.201.2.59:8103");


        InitialContext iniCtx = new InitialContext(p);
        Object tmp = iniCtx.lookup("jms/cf/gems");
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        conn = qcf.createQueueConnection();
        que = (Queue) iniCtx.lookup("gems/all/equipment/events");
        session = conn.createQueueSession(false,
                QueueSession.AUTO_ACKNOWLEDGE);
        conn.start();
    }

    public void sendRecvAsync(String text)
            throws JMSException,
            NamingException {
        logger.info("Begin sendRecvAsync");
        // Setup the PTP connection, session
        setupPTP();

        // Set the async listener
        //QueueReceiver recv = session.createReceiver(que);
        //recv.setMessageListener(new ExListener());

        // Send a text msg
        QueueSender send = session.createSender(que);
        TextMessage tm = session.createTextMessage(text);
        send.send(tm);
        logger.info("sendRecvAsync, sent text=" + tm.getText());
        send.close();
        logger.info("End sendRecvAsync");
    }

    public void stop()
            throws JMSException {
        conn.stop();
        session.close();
        conn.close();
    }

    public static void main(String args[])
            throws Exception {
        logger.info("Begin SendRecvClient, now=" + System.currentTimeMillis());
        JMSMessageSenderTest client = new JMSMessageSenderTest();
        client.sendRecvAsync("A text msg");
        client.stop();
        logger.info("End SendRecvClient");
        System.exit(0);
    }


}
