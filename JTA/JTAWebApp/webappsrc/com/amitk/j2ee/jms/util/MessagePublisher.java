package com.amitk.j2ee.jms.util;

import java.sql.Date;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import com.amitk.j2ee.jta.dao.AmitkJTADao;

public class MessagePublisher {

	private String qConnFactoryName;
	private String queueName; 
	private boolean isTransacted = false;
	
	
	private Context ctx;
	private QueueConnectionFactory qcf;
	private QueueConnection qCon;
	private QueueSession qSess;
	private Queue queue;
	private QueueSender qSender;
	

	public static UserTransaction getUserTransaction()
		throws NamingException{
		Context ctx = new InitialContext();
		UserTransaction ut = (UserTransaction)ctx.lookup("javax.transaction.UserTransaction");
		return ut;

	}
	
	public static void main(String[] args) {
		UserTransaction ut = null;
		try {
			  boolean abort = false;
			  System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			  System.setProperty(Context.PROVIDER_URL, "t3://localhost:7001");
			
			  ut = getUserTransaction();
			  ut.begin();

			  MessagePublisher mp = new MessagePublisher(	"jms/JTATestJMSConnectionFactory",
					  										"jms/JTATestJMSQueue",
					  										false
					  									);
			  mp.publishMessage("Hello JMS2");
			  mp.publishMessage("Hello again JMS2");
			  
			  new AmitkJTADao("jdbc/JTATestDataSource").updateRecord(1, new Date(System.currentTimeMillis()));
			  
			  if(abort)
				  throw new Exception("Forcefully aborting transaction");
			  
			  ut.commit();
			  
			
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(null != ut)
					ut.rollback();
			} catch (SystemException se) {
				se.printStackTrace();
			}finally{
				ut = null;
			}
		}
		  
	}
	
	
	public MessagePublisher(String qConnFactoryName,String queueName, boolean isTransacted){
		this.qConnFactoryName = qConnFactoryName;
		this.queueName = queueName;
		this.isTransacted = isTransacted;
	}
	
	private void setInitialContext()
		throws NamingException{
		if(null == ctx)
			ctx = new InitialContext();
	}
	
	private void setConnectionFactory()
		throws NamingException{
		if(null == qcf){
			//setInitialContext();
			qcf = (QueueConnectionFactory) ctx.lookup(qConnFactoryName);
		}
	}
	
	private void createQueueConnection()
		throws JMSException{
		if(null == qCon)
			qCon = qcf.createQueueConnection();
	}
	
	
	private void createQueueSession()
		throws JMSException{
		if(qSess == null){
			qSess = qCon.createQueueSession(isTransacted, Session.AUTO_ACKNOWLEDGE);
		}
	}
	
	private void lookupQueue()
		throws NamingException{
		if(null == queue)
			queue = (Queue) ctx.lookup(queueName);
		
	}
	
	private void createQueueSender()
		throws JMSException{
		if(null == qSender)
			qSender = qSess.createSender(queue);
	}
	
	private TextMessage createTextMessage(String text)
		throws JMSException{
		return qSess.createTextMessage(text);
		
	}
	public boolean publishMessage(String textMessage)
		throws NamingException, JMSException{
		setInitialContext();
		setConnectionFactory();
		createQueueConnection();
		createQueueSession();
		lookupQueue();
		createQueueSender();
		
		qSender.send(createTextMessage(textMessage));
		
		return true;
	}

}
