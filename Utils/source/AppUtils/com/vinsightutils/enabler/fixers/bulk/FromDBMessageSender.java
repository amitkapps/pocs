package com.vinsightutils.enabler.fixers.bulk;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.vinsightutils.common.Constants;
import com.shared.utils.FileUtil;
import com.shared.utils.GenUtils;
import com.shared.utils.Log4JUtil;
import com.shared.utils.LogUtil;

public class FromDBMessageSender {

	private static Logger log = Logger.getLogger(FromDBMessageSender.class);
	public FromDBMessageSender() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String logFileDir = "C:\\AmitK\\work\\project\\VINSight\\OtherWork\\DataFixing\\" ;
		String logFileSubPath = "Specific\\OUTCNTR since long\\2006-12-05\\";
		String logFilePath = logFileDir + logFileSubPath + "UnitsOUTCNTRAtDestinationIn2005.log";
		
		String appInstance = Constants.APP_INSTANCE_PROD;	//******************************
		boolean isIncomingMessage = false;	//***************************
		String evtMessageType = VSMessageGenerator.EVT_AVAILABLE_MANF_SET;//*****************
		int[] unitIds 
			= {10179256,10194021,10195582,10198275,10198463,10199773,10200549,10200550,10205704,10205705,10207573,10207770,10207964,10208565,10209680,10211538,10211539,10211540,10211541,10213144,10213146,10213589,10213590,10213591,10213592,10213593,10216855,10217933,10217934,10217936,10217937,10217938,10217939,10217940,10217941,10217942,10217943,10224359,10244035,10310590,10311523,10317936,10324642,10325565,10326849,10328273,10335022,10338558,10339212};	//*************************
		
			try{
				//create folder for logging
				FileUtil.createFoldersRecursively(logFileDir + logFileSubPath);
				//add log file as an appender
				Log4JUtil.addFileAppenderToRootForThisTask(logFilePath, true);
				
				//Get messages from DB
				ArrayList evtMessageList = 
				VSMessageGenerator.getEVTMsgForUnits(	appInstance,
														evtMessageType, 
														unitIds
												   );
				LogUtil.logContentsOnEachLine
							(evtMessageList, "Generated evtMessages for unitIds:" 
								+ GenUtils.getCharDelimitedStr(unitIds, ",") 
							);
				
				//post message to Queue
				//VSEnablerBulkMessageSender.postMessages(evtMessageList, appInstance, isIncomingMessage);
				
			}catch(Exception ex){
				log.error(ex);
			}
		}
	
	}
