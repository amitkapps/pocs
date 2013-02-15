package poc.mq.client;

import com.ibm.mq.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * Servlet implementation class for Servlet: EirMqUpdater
 * @author Wei Mao 6/21/2008
 */
public class EirMqUpdater {
	private static final long serialVersionUID = 1L;
	private Updater updater;
	private static Map applicationParameters = new HashMap();

    static{
        applicationParameters.put("EIR_UPDATER_INTERVAL", 10000L);
        System.out.println("Eir updater sleeping interval is updated to " + 10 + " seconds.");
    }
	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#HttpServlet()
	 */
	public EirMqUpdater() {
		super();
	}   	


	/* (non-Java-doc)
	 * @see javax.servlet.http.HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void init(String action){
		try {

			if("start".equalsIgnoreCase(action)) {
				startUpdater();
				System.out.println("Eir updater is started.");
			}else if("stop".equalsIgnoreCase(action)){
				stopUpdater();
				System.out.println("Eir updater is stopped.");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void destroy() {
		try{
			stopUpdater();
			System.out.println("Eir updater is stopped due to servlet destroy.");
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	private void startUpdater() throws Exception{
		stopUpdater();
		updater = new Updater();
		updater.setDaemon(true);
		updater.start();
	}

	private void stopUpdater() throws Exception{
		if (updater != null) {
			updater.stop = true;
			updater.interrupt();
			updater.join(5000);
		}
	}

	private void getMQMessages(){
		boolean hasMessages = true;
		MQQueueManager qMgr = null;
		MQQueue queue = null;	

		try {
			MQEnvironment.hostname = "10.8.7.145";
			MQEnvironment.channel = "EIR.SVRCON";
			MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
			String queueManagerName = "UNXDEV1";
			
			int openOptions = MQC.MQOO_INPUT_AS_Q_DEF | MQC.MQOO_FAIL_IF_QUIESCING;
			qMgr = new MQQueueManager(queueManagerName);		
			queue = qMgr.accessQueue("EIR.FROM.ACETS",   // queue name
									 openOptions,           // open options
									 queueManagerName,      // queue manager
									 null,                  // dynamic q name
									 null				      // alternate user id
			);
		
			MQGetMessageOptions messageOptions = new MQGetMessageOptions();
			messageOptions.matchOptions = MQC.MQMO_NONE; // do not match message id's
			MQMessage mqMessage = new MQMessage();
			String message = null;
			
			while (hasMessages) {
				mqMessage.clearMessage();
				try {
					queue.get(mqMessage, messageOptions);
					message = mqMessage.readString(mqMessage.getMessageLength());
					System.out.println("EIR Message: " + message);
				}catch (MQException e) {
					if (e.reasonCode == 2033) {//No more messages in the queue
						hasMessages = false;
					} else { 
						System.out.println("An MQException has occured while attempting to " +
									 "get the message. REASON CODE: " + e.reasonCode);
                        e.printStackTrace();
					}
				}catch(Exception ex){
					System.out.println("Processing EIR update message failed: ");
                    ex.printStackTrace();
				}
			}
		} catch(IOException e){
			System.out.println("An IOException: "); e.printStackTrace();
		} catch (MQException e) {
			System.out.println("An MQException has occured. REASON CODE: " + e.reasonCode); e.printStackTrace();
		} catch(Exception e){
			System.out.println("An Exception has occured. REASON CODE: "); e.printStackTrace();
		} finally {
			try{
				queue.close();
				queue = null;
			}catch (Exception e) {}
			try{
				qMgr.disconnect();
				qMgr = null;
			}catch(Exception e){}
		}
	}
	
	public static void main(String[] args){
		EirMqUpdater updater = new EirMqUpdater();
		try{
			updater.startUpdater();

            Thread.sleep(10000000);
			updater.stopUpdater();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	private class Updater extends Thread{
		private boolean stop;
		
		private Updater(){
			super("eirUpdater");
		}
		
		public void run(){
			System.out.println("Eir updater is running...");
			while(!stop){
				getMQMessages();
				try{
					//System.out.println("Eir updater is going to sleep.");
					sleep(10000);
				}catch(InterruptedException ex){
					stop = true;
				}catch(Exception ex){
					stop = true;
					System.out.println("Eir updater sleeping error: ");
                    ex.printStackTrace();
				}
			}
			System.out.println("Eir updater is stopped.");
		}
	}
}