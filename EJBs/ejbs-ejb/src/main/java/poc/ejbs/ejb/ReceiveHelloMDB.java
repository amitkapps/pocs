package poc.ejbs.ejb;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 2/20/13
 * Time: 12:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ReceiveHelloMDB implements MessageDrivenBean, MessageListener {
    Logger logger = Logger.getLogger(ReceiveHelloMDB.class.getCanonicalName());

    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void ejbRemove() throws EJBException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void onMessage(Message message) {
        try {
            logger.info("Message received: " + ((TextMessage)message).getText());
        } catch (JMSException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
