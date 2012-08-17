package com.vinsightutils.enabler.fixers.bulk;

import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.Context;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.shared.utils.FileUtil;
import com.shared.utils.Log4JUtil;
import com.vinsightutils.common.Constants;
import com.vinsightutils.enabler.utils.UpdateQueryOutputVOList;

public class FromFileDBUpdater {

	private static Logger log = Logger.getLogger(FromFileDBUpdater.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String basePath = "C:\\AmitK\\work\\project\\VINSight\\OtherWork\\DataFixing\\";
		String subPath = basePath + "Specific\\DlrForExistingTBookings\\";//*******************************
		String updateSQLFilePath = subPath + "2Upd_Prod_TBooking_with_dealer_list.sql"; //************
		boolean isCommit = true; //***************************
		//String appInstance = Constants.APP_INSTANCE_PROD / Constants.APP_INSTANCE_DEV;
		String appInstance = Constants.APP_INSTANCE_PROD;	//******************************
		String environment = Constants.APP_ENV_VS;	//*************************
		
		Log4JUtil.addFileAppenderToRootForThisTask(updateSQLFilePath + ".log", false);
		
		try{
			ArrayList updQrys = FileUtil.getFileContentsAsAL(updateSQLFilePath);
			UpdateQueryOutputVOList updQryOPVOList
				= VSEnablerBulkDBUpdater
					.runUpdateQueries(updQrys, appInstance, environment, isCommit, true, 500);
			
			log.info("Queries Summary for file: "+updateSQLFilePath+"\n" );
			log.info(updQryOPVOList.getShortSummary());
			log.info(updQryOPVOList.getRecordWiseLongSummary());
		}catch(Exception ex){
			ex.printStackTrace();
			log.fatal("Message posting to enabler failed!!", ex);
		}
	}

}
