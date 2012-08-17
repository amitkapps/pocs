package com.shared.utils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import javax.jms.DeliveryMode;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSender;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;

import weblogic.jndi.Environment;

public class QueueBrowserUtil {

	
	//Local Server Entries
	private static String providerURL = "t3://localhost:7001";
	private static String securityPrincipal = "system";
	private static String securityCredential = "weblogic";
	
	//DEV Server Entries
//	private static String providerURL = "t3://10.8.7.145:9001";
//	private static String securityPrincipal = "system";
//	private static String securityCredential = "falcon145";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			String msg = "UPDATE IDB2.MASTER SET VEH_VES_CODE = 'MWI' , VEH_VOY_NO = '039', VEH_DIRECTION = 'W' , VEH_VES_STOW_DATE = DATE('04/25/2006'), VEH_VES_STOW_LOC = 'GARAGE' , VEH_VES_STOW_TYPE = 'C', VEH_YARD_STATUS = 'C', VEH_VES_STOW = '' , VEH_CMIS_EXTRACT = 'R' , VEH_VES_ARRV_DATE='0001-01-01' , VEH_VES_SAIL_DATE='0001-01-01'  WHERE VEH_SHIPPERCLASS = 'SSS' AND VEH_LOAD_PORT = 'OAK'  AND VEH_AKL = 'V4696'  AND VEH_DATE_RECEIVED = DATE('02/03/2006')";
			//postMessage(msg);

			//receiveNextMessage(true, 10000);
			browseMessages(true);
			
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	
	public static void browseMessages(boolean showTextMessage) throws Exception{

		String queueName = "jms/UpdateDBQueue";
		QueueConnectionFactory factory=null;
		Queue queue=null; 
		QueueSession qsession = null;
		QueueConnection qcon=null;
		Context ic=null;

		Environment env = new Environment();
		//env.setSecurityPrincipal(securityPrincipal);
		//env.setSecurityCredentials(securityCredential);
		env.setProviderUrl(providerURL); 
		env.setInitialContextFactory("weblogic.jndi.WLInitialContextFactory");

		ic = (javax.naming.Context)env.getInitialContext();
		factory = (QueueConnectionFactory) ic.lookup("jms/WLQueueConnectionFactory");
		queue = (Queue) ic.lookup(queueName); 

		qcon= factory.createQueueConnection();

		/*creating a queue sessionobject*/ 
		System.out.println("Attempting to browse queue: "+ queueName + " on " + providerURL); 
		qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		QueueBrowser queueBrowser = qsession.createBrowser(queue);
		Enumeration messageEnum = queueBrowser.getEnumeration();
		
		 Message msg;
		 int count = 0;
		 while (messageEnum.hasMoreElements()) {
			 msg = (Message) messageEnum.nextElement();
			 Enumeration propNames = msg.getPropertyNames();
			 System.out.println("\nFound Message :" + (++count) + " of type " + msg.getJMSType() + ", Properties : ");
			 System.out.println(" -ClassName:" + msg.getClass().getName() + "\n" +
					 			" -CorrelationID:" + msg.getJMSCorrelationID() + "\n" +
					 			" -Destination:" + msg.getJMSDestination() + "\n" +
					 			" -DeliveryMode:" + msg.getJMSDeliveryMode() + "\n" +
					 			" -Expiration:" + msg.getJMSExpiration() + "\n" +
					 			" -JMSID:" + msg.getJMSMessageID() + "\n" +
					 			" -Priority:" + msg.getJMSPriority() + "\n" +
					 			" -Redelivered:" + msg.getJMSRedelivered() + "\n" +
					 			" -Timestamp:" + new Timestamp(msg.getJMSTimestamp()) + "\n" +
					 			" -PropertyNames:" + enumToCSV(propNames)
					 			);
			 if (msg instanceof TextMessage && showTextMessage)
				 System.out.println("Message " + count + ": " + ((TextMessage) msg).getText());
		 }
		 
		 if(count == 0)
			 System.out.println("No messages on the queue.");
	}
	

  public static void postMessage(String messageText) {
	  String queueName = "jms/UpdateDBQueue";
	    QueueConnection connection = null;
	    QueueConnectionFactory factory = null;
	    QueueSession session = null;
	    QueueSender sender = null;

	    try {
	      TextMessage msg;

			Environment env = new Environment();
			env.setSecurityPrincipal(securityPrincipal);
			env.setSecurityCredentials(securityCredential);
			env.setProviderUrl(providerURL); 
			env.setInitialContextFactory("weblogic.jndi.WLInitialContextFactory");

			Context ctx = (javax.naming.Context)env.getInitialContext();
	      factory = (QueueConnectionFactory) ctx.lookup("jms/WLQueueConnectionFactory");
	      connection = factory.createQueueConnection();

	      connection.start();

	      Queue acetsQueue = (Queue) ctx.lookup(queueName);

	      session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);

	      sender = session.createSender(acetsQueue);

	      msg = session.createTextMessage();

	      msg.setText(messageText);
	      System.out.println("Attempting to post message on Queue " + queueName + " on " + providerURL + "\n" + msg );
	      sender.send(msg);

	      System.out.println("Successfully posted.");

	    } catch (Exception e) {

	    	System.out.println("Exception posting message!!");
	    	e.printStackTrace();

	    } finally {

	      try { sender.close(); } 
	      catch (Exception e) {
	    	  e.printStackTrace();
	      }

	      try { session.close(); } catch (Exception e) {
	    	  e.printStackTrace();
	      }

	      try { connection.close(); } catch (Exception e) {
	    	  e.printStackTrace();
	      }

	    }

  }

	public static void receiveNextMessage(boolean wait, long waitTime) throws Exception{


		QueueConnectionFactory factory=null;
		Queue queue=null; 
		QueueSession qsession = null;
		QueueConnection qcon=null;
		Context ic=null;

		Environment env = new Environment();
		env.setSecurityPrincipal(securityPrincipal);
		env.setSecurityCredentials(securityCredential);
		env.setProviderUrl(providerURL); 
		env.setInitialContextFactory("weblogic.jndi.WLInitialContextFactory");

		ic = (javax.naming.Context)env.getInitialContext();
		factory = (QueueConnectionFactory) ic.lookup("jms/WLQueueConnectionFactory");
		queue = (Queue) ic.lookup("jms/UpdateDBQueue"); 

		qcon= factory.createQueueConnection();

		/*creating a queue sessionobject*/ 
		System.out.println("Queue Connection class name: "+qcon.getClass().getName()); 
		qsession = qcon.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
		QueueReceiver queueReceiver = qsession.createReceiver(queue);
		Message msg = null;
		
		if(!wait){
			System.out.println("Retrieving message immediately if available.");
			msg = queueReceiver.receiveNoWait();
		}
		else {//if we want to wait
			if(waitTime >= 0){
				System.out.println("Waiting until " + waitTime + " millisecs to receive a message.");
				msg = queueReceiver.receive(waitTime);
			}//no wait time specified
			else{
				System.out.println("Waiting indefinitely to receive a message.");
				msg = queueReceiver.receive();
			}
		}
		qcon.close();
		if(null == msg)
			System.out.println("No message retreived.");
		else
			System.out.println("Retrieved a message of type " + msg.getClass().getName());

		 if (msg instanceof TextMessage)
			 System.out.println("Text Message Received: " + ((TextMessage) msg).getText());
	}
	
	private static final String enumToCSV(Enumeration enum){
		String csv = "";
		while(enum.hasMoreElements())
			csv += enum.nextElement();
		return csv;
	}
}
