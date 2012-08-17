package com.vinsightutils.enabler.fixers.bulk;

import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.Context;

import org.apache.log4j.Logger;

import com.shared.utils.FileUtil;
import com.vinsightutils.common.Constants;
import com.vinsightutils.enabler.utils.VSEnablerFacade;

public class VSEnablerBulkMessageSender {

	private static Logger log = Logger.getLogger(VSEnablerBulkMessageSender.class);
	public VSEnablerBulkMessageSender() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * posts messages from the file.
	 * each line on the file is considered a separate message
	 * @param isIncomingMessage true if posting to VS queue, false if posting to ACETS queue.
	 */
	public static void postMessages(String messageFilePath, String appInstance, boolean isIncomingMessage) 
	throws Exception{
		
		try{
			ArrayList messages = FileUtil.getFileContentsAsAL(messageFilePath);
			postMessages(messages, appInstance, isIncomingMessage);
		}catch(Exception ex){
			log.error("Message posting to enabler failed!!", ex);
			throw ex;
		}
	}

	/**
	 * posts messages to VSEnabler.
	 * each element of the ArrayList is considered as a separate message
	 */
	public static void postMessages(ArrayList messageList, String appInstance, boolean isIncomingMessage) 
	throws Exception{
		
		try{
			int count = 0;
			if(null != messageList && messageList.size() > 0){
				for (Iterator iter = messageList.iterator(); iter.hasNext();) {
					String message = (String) iter.next();
					log.debug(message);
					String output = VSEnablerFacade.postMessageNReturnResult(appInstance, message, isIncomingMessage);
					log.debug(output);
					log.debug("###DONE " + ++count + " MESSAGES ####");
					Thread.currentThread().sleep(1000);
				}
			}
		}catch(Exception ex){
			log.fatal("Message posting to enabler failed!!", ex);
			throw ex;
		}
	}
	
	
}
