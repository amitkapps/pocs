package com.vinsightutils.enabler.fixers.bulk;

import java.util.ArrayList;
import java.util.Iterator;

import javax.naming.Context;

import org.apache.log4j.Logger;

import com.shared.utils.FileUtil;
import com.vinsightutils.common.Constants;
import com.vinsightutils.enabler.utils.UpdateQueryOutputVO;
import com.vinsightutils.enabler.utils.UpdateQueryOutputVOList;
import com.vinsightutils.enabler.utils.VSEnablerFacade;
import com.vinsightutils.enabler.utils.VSEnablerFacadeHelper;

import sun.nio.cs.ext.ISCII91;

public class VSEnablerBulkDBUpdater {

	private static Logger log = Logger.getLogger(VSEnablerBulkDBUpdater.class);
	public VSEnablerBulkDBUpdater() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * Runs Update queries from a file.
	 * each line on the file is considered a separate SQL command.
	 * @param doCommit false if you dont wanna commit
	 */
	public UpdateQueryOutputVOList runUpdateQueries(String updateQueryFilePath, String appInstance,String environment, boolean isCommit, boolean isVerbose, int sleepTimeMillis) 
	throws Exception{
		
		try{
			ArrayList queries = FileUtil.getFileContentsAsAL(updateQueryFilePath);
			
			return runUpdateQueries(queries, appInstance, environment, isCommit, isVerbose, sleepTimeMillis);
			
			
		}catch(Exception ex){
			log.error("Message posting to enabler failed!!", ex);
			throw ex;
		}
	}

	/**
	 * posts messages to VSEnabler.
	 * each element of the ArrayList is considered as a separate message
	 */
	public static UpdateQueryOutputVOList runUpdateQueries(	ArrayList queries, 
															String appInstance,
															String environment, 
															boolean isCommit,
															boolean isVerbose,
															int sleepTimeMillis) 
	throws Exception{
		UpdateQueryOutputVOList updQryOpObjList = new UpdateQueryOutputVOList();
		try{
			int count = 0;
			long startTime = 0;
			long totalTime = 0;
			if(null != queries && queries.size() > 0){
				int total = queries.size();
				for (Iterator iter = queries.iterator(); iter.hasNext();) {
					count++;
					String query = (String) iter.next();
					startTime = System.currentTimeMillis();
					ArrayList response = VSEnablerFacade.postQueryNReturnResult(appInstance, environment, Constants.QUERY_TYPE_UPD, isCommit, query);
					totalTime = System.currentTimeMillis() - startTime;
					UpdateQueryOutputVO updQryOPObj = VSEnablerFacadeHelper.parseUpdQryResponse(query, totalTime, response, isCommit);
					if(isVerbose)
						log.debug("("+count + "/" + total +"):"+  updQryOPObj.getSummary());
					updQryOpObjList.add(updQryOPObj);
					Thread.currentThread().sleep(sleepTimeMillis);
				}
			}
		}catch(Exception ex){
			log.fatal("Message posting to enabler failed!!", ex);
			throw ex;
		}
		return updQryOpObjList;
	}
	
	
}
