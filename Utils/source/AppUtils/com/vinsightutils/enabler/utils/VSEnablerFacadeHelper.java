package com.vinsightutils.enabler.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.vinsightutils.common.Constants;
import com.shared.utils.GenericQueryResultVO;

public class VSEnablerFacadeHelper {
	
	private static Logger logger = Logger.getLogger(VSEnablerFacadeHelper.class); 
	
	public static final String SELECT_QUERY_SUCCESS = "success";
	public static final String SELECT_QUERY_FAILED = "FAILED";
	
	/**
	 * Parses the select query response (From VINSight Enabler) and generates the <code>SelectQueryOutputVO</code> object. 
	 *
	 * @param query - the passed query
	 * @param timeTaken - the time taken for the query
	 * @param response - the response received from the VinSight enabler on posting a SELECT QUERY.
	 */
	public static SelectQueryOutputVO parseSelQryResponse(String query, long timeTaken, ArrayList response)throws Exception{
		SelectQueryOutputVO selQryOutputVO = null;
		boolean isSuccessFul = false;
		GenericQueryResultVO qryResVO= null;
		
		isSuccessFul = isSelQrySuccess(response);
		if (isSuccessFul) {
			ArrayList colNames = getSelQryColNames(response);
			ArrayList colValues = getSelQryColValues(response);
			qryResVO = new GenericQueryResultVO(colNames, colValues);
			
			selQryOutputVO = new SelectQueryOutputVO(query,
													timeTaken, 
													VSEnablerFacadeHelper.SELECT_QUERY_SUCCESS,
													response,
													qryResVO);
		}else {
			selQryOutputVO = new SelectQueryOutputVO(query,
													timeTaken, 
													VSEnablerFacadeHelper.SELECT_QUERY_FAILED,
													response,
													null);
		}
		
		return selQryOutputVO;
	} 
	
	private static final String SEL_NO_OF_ROWS_PROCESSED_STR = "(Q) Number of rows processed:";
	
	private static boolean isSelQrySuccess(ArrayList response){
		boolean isSuccessful = false;
		if (null==response || response.size()==0) {
			return false;
		}
		
		for (Iterator iter = response.iterator(); iter.hasNext();) {
			String line = (String) iter.next();
			if(line.startsWith(VSEnablerFacadeHelper.SEL_NO_OF_ROWS_PROCESSED_STR)){
				isSuccessful= true;
				break;
			}
		}//end for
		
		return isSuccessful;
	}
	
	private static final String QRY_RESPONSE_DELIM = ","; 
	private static ArrayList getSelQryColNames(ArrayList response){
		ArrayList colNames = new ArrayList(); 
		
		StringTokenizer colTkns = new StringTokenizer(	(String) response.get(1), 
														VSEnablerFacadeHelper.QRY_RESPONSE_DELIM);
		for(; colTkns.hasMoreTokens();){
			colNames.add(colTkns.nextToken());
		}
		return colNames;
	}
	
	private static final String BLANK_STRING = "BLANK_STRING";
	private static ArrayList getSelQryColValues(ArrayList response){
		ArrayList rowsAsAL = new ArrayList();
		ArrayList colValues = null;
		
		StringTokenizer colTkns = null;
		
		for(int rowCtr=2; rowCtr < (response.size() - 5); rowCtr++){
			//logger.debug("processing row-" + response.get(rowCtr));
			colValues = new ArrayList();
			String rowString = (String)response.get(rowCtr);
			rowString = rowString.replaceAll(",,","," + BLANK_STRING + ",").replaceAll(",,","," + BLANK_STRING + ",");
			colTkns = new StringTokenizer( rowString, VSEnablerFacadeHelper.QRY_RESPONSE_DELIM );
			while(colTkns.hasMoreTokens()){
				String colValue = colTkns.nextToken(); 
				colValues.add( colValue.equals(BLANK_STRING) ? "" : colValue );
			}
			
			//logger.debug("adding a new row with cols="+colValues.size());
			rowsAsAL.add(colValues);
		}
		//logger.debug("added rows="+rowsAsAL.size());
		return rowsAsAL;
		
	}

	public static final String UPD_QRY_SUCCCESS_IDENTIFIER = "(U) Number of rows processed: ";
	public static UpdateQueryOutputVO parseUpdQryResponse(String query, long timeTaken, ArrayList response, boolean isCommit){
		String execStatus = Constants.QUERY_STATUS_FAILURE;
		int rowsUpdated = -1;
		
		if(		null != response 
			&&	response.size() > 0)
		{
			for (Iterator iter = response.iterator(); iter.hasNext();) {
				String responseLine = (String) iter.next();
				if (responseLine.startsWith(UPD_QRY_SUCCCESS_IDENTIFIER)) {
					execStatus = Constants.QUERY_STATUS_SUCCESS;
					rowsUpdated = Integer.parseInt(responseLine.substring(responseLine.lastIndexOf(" ") + 1));
					break;
				}
			}
		}
		return new UpdateQueryOutputVO(query, timeTaken, execStatus, response, rowsUpdated, isCommit);
	}
	
 }
