package com.shared.utils;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

public class Log4JUtil {
	private static Logger logger = Logger.getLogger(Log4JUtil.class);

	/**
	 * Add a file appender with a predefined pattern to the root logger for this
	 * log4j session.
	 * 
	 * @param filePath the file path.
	 * @param append false means overwrite the old file (be careful!!)
	 */
	public static void addFileAppenderToRootForThisTask(String filePath, boolean append){
		try{
			PatternLayout patLayout = new PatternLayout("%d{HH:mm:ss,sss} [%-5p]- [%F- %M(%L)] - %m%n");
			FileAppender fileAppender = new FileAppender(patLayout, filePath, append); 
			Logger.getRootLogger().addAppender(fileAppender);
		}
		catch(Exception Ex){
			logger.error("unable to add appender!", Ex);
		}
	}
	
	/**
	 * Add a console appender with a predefined pattern to the root logger for this
	 * log4j session.
	 */
	public static void addConsoleAppenderToRootForThisTask(){
		try{
			PatternLayout patLayout = new PatternLayout("%d{HH:mm:ss,sss} [%-5p]- [%F- %M(%L)] - %m%n");
			ConsoleAppender consoleAppender = new ConsoleAppender(patLayout);
			Logger.getRootLogger().addAppender(consoleAppender);
		}
		catch(Exception Ex){
			logger.error("unable to add appender!", Ex);
		}
	}
	

}
