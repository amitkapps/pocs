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
        //p.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        p.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        //p.put(Context.PROVIDER_URL, "t3://10.201.2.59:8103");
        p.put(Context.PROVIDER_URL, "jnp://10.8.7.121:1099/");

        InitialContext iniCtx = new InitialContext(p);
        Object tmp = iniCtx.lookup("jms.cf.eir.wl");
        logger.info("Obtained EirMQConnectionFactory " + tmp);
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        conn = qcf.createQueueConnection();
        que = (Queue) iniCtx.lookup("jms.queue.eir.to.facts");
        session = conn.createQueueSession(false,
                QueueSession.AUTO_ACKNOWLEDGE);
        conn.start();
    }

    public void sendMessage(String text)
            throws JMSException,
            NamingException {
        logger.info("Begin sendMessage");
        // Setup the PTP connection, session
        setupPTP();

        // Set the async listener
        //QueueReceiver recv = session.createReceiver(que);
        //recv.setMessageListener(new ExListener());

        // Send a text msg
        QueueSender send = session.createSender(que);
        TextMessage tm = session.createTextMessage(text);
        send.send(tm);
        logger.info("sendMessage, sent text=" + tm.getText());
        send.close();
        logger.info("End sendMessage");
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
        client.sendMessage("MATU457203:70004  :376232    :201210290438");
        client.stop();
        logger.info("End SendRecvClient");
        System.exit(0);
    }


}
