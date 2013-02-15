package amitk.poc.jms.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: axk
 * Date: 9/23/12
 * Time: 1:16 AM
 * To change this template use File | Settings | File Templates.
 */
public class OrderMessagesListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
        logger.info("Setting up Order Message Listener");
        try {
            startOrderMessageListener();
        } catch (Exception e) {
            logger.error("Error starting Order Message Listener", e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //To change body of implemented methods use File | Settings | File Templates.
        try {
            conn.stop();
            session.close();
            conn.close();
        } catch (JMSException e) {
            logger.error("Error stopping message listener", e);
        }

    }




    private static Logger logger = LoggerFactory.getLogger(OrderMessagesListener.class);

    QueueConnection conn;
    QueueSession session;
    Queue que;

    public static class ExListener
            implements MessageListener {
        private static Logger logger = LoggerFactory.getLogger(ExListener.class);

        public void onMessage(Message msg) {
            TextMessage tm = (TextMessage) msg;
            try {
                logger.info("Received text message=" + tm.getText());
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }

    private void setupQueueConnection()
            throws JMSException,
            NamingException {

        Properties p = new Properties();
/*
        p.put(Context.INITIAL_CONTEXT_FACTORY,
                "org.jnp.interfaces.NamingContextFactory");
        p.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
        p.put(Context.PROVIDER_URL, "jnp://10.8.7.142:1099/");
*/


        InitialContext iniCtx = new InitialContext(p);
        Object tmp = iniCtx.lookup("jms/cf/ordersystem");
        QueueConnectionFactory qcf = (QueueConnectionFactory) tmp;
        conn = qcf.createQueueConnection();
        que = (Queue) iniCtx.lookup("jms/queue/orders");
        session = conn.createQueueSession(false,
                QueueSession.AUTO_ACKNOWLEDGE);
        conn.start();
    }

    private void startOrderMessageListener()
            throws JMSException,
            NamingException {
        logger.info("Begin receive messages");
        // Setup the PTP connection, session
        setupQueueConnection();

        // Set the async listener
        QueueReceiver recv = session.createReceiver(que);
        recv.setMessageListener(new ExListener());
    }


}
