package com.vinsightutils.enabler.fixers.bulk;

import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.Context;

import org.apache.log4j.Logger;

import com.vinsightutils.common.Constants;
import com.shared.utils.FileUtil;
import com.shared.utils.LogUtil;
import com.vinsightutils.enabler.utils.VSEnablerFacade;
import com.vinsightutils.enabler.utils.VSEnablerFacadeHelper;

public class FromFileMessageSender {

	private static Logger log = Logger.getLogger(FromFileMessageSender.class);
	public FromFileMessageSender() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	
		String basePath = "C:\\AmitK\\work\\project\\VINSight\\OtherWork\\DataFixing\\Specific\\";
		String subPath = basePath + "OUTCNTR since long\\";//*******************************
		String messageFileName = "Event logs for Units Out Cntr at Dest in 2006.txt";
		//CAREFUL!!!! MESSAGES WILL BE POSTED IN ONE GO
		String messageFilePath = subPath + messageFileName; //************
		boolean isIncomingMessage = false; //***************************
		//String appInstance = Constants.APP_INSTANCE_PROD / Constants.APP_INSTANCE_DEV;
		String appInstance = Constants.APP_INSTANCE_PROD;//******************************

		try{
			LogUtil.initiateLogging(subPath, messageFileName + ".log", true);
			log.debug("Posting message to archive in " + appInstance + ", from" + messageFilePath);
			VSEnablerBulkMessageSender.postMessages(messageFilePath, appInstance, isIncomingMessage);
		}catch(Exception ex){
			ex.printStackTrace();
			log.fatal("Message posting to enabler failed!!", ex);
		}
	}

}
