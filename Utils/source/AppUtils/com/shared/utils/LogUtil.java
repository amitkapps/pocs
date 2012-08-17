package com.shared.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class LogUtil {
	private static Logger logger = Logger.getLogger(LogUtil.class);

	
	public static void initiateLogging(String logFileDir, String logFileName, boolean doAppend)
		throws Exception{
			FileUtil.createFoldersRecursively(logFileDir);
			String logFilePath = logFileDir + logFileName ;
			Log4JUtil.addFileAppenderToRootForThisTask(logFilePath, true);
			Log4JUtil.addConsoleAppenderToRootForThisTask();
	}

	
	
	public static void logContentsOnEachLine(ArrayList contentList, String header){
		
		String size = (null == contentList) ? "null" : String.valueOf(contentList.size());
		
		if(null != header)
			logger.debug(header + ", Size:" + size);
		if(null != contentList){
			for (Iterator iter = contentList.iterator(); iter.hasNext();) {
				Object element = (String) iter.next();
				logger.debug(element);
				
			}
		}
			
	}
	
	public static void logContentsOnEachLine(ArrayList contentList){
		
		String size = (null == contentList) ? "null" : String.valueOf(contentList.size());
		
		logger.debug("Logging ArrayList elements within[], Size:" + size);
		if(null != contentList){
			for (Iterator iter = contentList.iterator(); iter.hasNext();) {
				Object element = (String) iter.next();
				logger.debug("["+element+"]");
				
			}
		}
			
	}
	
}
