package com.vinsightutils.enabler.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import com.shared.utils.GenericQueryResultVO;
import com.shared.utils.ArrayListUtil;
import com.vinsightutils.enabler.utils.VSEnablerFacadeHelper;

/**
  * element[0] = to hold the time taken for executing the Query in millis
  * element[1] = <Status of query execution> success/FAILED 
  * element[2] = <response received from the VS Enable servlet> <code>ArrayList</code> - each element is a line found in the response
  * element[3] = the <code>GenericQueryResultVO</code>, null if the query failed.
  */

public class SelectQueryOutputVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String query = null;
	private long timeTaken = 0;
	private String executionStatus = "";
	ArrayList VSEnablerResponse = null;
	GenericQueryResultVO genericQueryResultVO = null;
	
	public SelectQueryOutputVO( 	String query,
								long timeTaken,
								String executionStatus,
								ArrayList VSEnablerResponse,
								GenericQueryResultVO genericQueryResultVO
								){
		this.query = query;
		this.timeTaken = timeTaken;
		this.executionStatus = executionStatus;
		this.VSEnablerResponse = VSEnablerResponse;
		this.genericQueryResultVO = genericQueryResultVO;
		
	}
	
	/**
	 * returns the Vinsight enabler's Select query response as a single string 
	 * comprising of the response lines separated by '\n'
	 * @return
	 */
	public String getQueryResultAsString(){
		return ArrayListUtil.getALAsStringsWithCR(VSEnablerResponse);
	}
	/**
	 * Was the select query a success?
	 * @return
	 */
	public boolean isSuccess(){
		if(VSEnablerFacadeHelper.SELECT_QUERY_SUCCESS.equals(this.executionStatus)){
			return true;
		}else return false;
	}
	
	public String getFailureMessageAsString(){
		if(isSuccess())
			return null;
		else{
			ArrayList errorContents = (ArrayList) VSEnablerResponse.clone();
			errorContents.remove(0);
			errorContents.remove( errorContents.size() -1 );
			return ArrayListUtil.getALAsStringsWithCR(errorContents);
		}
	}
	
	public String getQueryResultAsHTML(){
		StringBuffer htmlBuf = new StringBuffer("<b>QUERY:</b><br />[" + query + "]<br/><b>Status=["+ this.getExecutionStatus() +"]<br/><b>RESULTS:</b><br/>");
		if (isSuccess()){
			htmlBuf.append(this.getGenericQueryResultVO().getRowsAsHTML());
		}else {
			htmlBuf.append("<textarea rows=\"10\" cols=\"90\" style=\"font-size: 9\">" + this.getQueryResultAsString() + "</textarea>");
		}
		return htmlBuf.toString(); 
	}
	/**
	 * whether the select query executed successfully or not
	 * @return
	 */
	public String getExecutionStatus() {
		return executionStatus;
	}
	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
	}
	
	/**
	 * Before calling this check if the query succeeded
	 * <code>SelectQueryOutputVO.isSuccess()</code> if it is false, this method will return null.
	 * @return <code>GenericQueryResultVO</code>
	 */
	public GenericQueryResultVO getGenericQueryResultVO() {
		return genericQueryResultVO;
	}
	public void setGenericQueryResultVO(GenericQueryResultVO genericQueryResultVO) {
		this.genericQueryResultVO = genericQueryResultVO;
	}
	public long getTimeTaken() {
		return timeTaken;
	}
	public void setTimeTaken(long timeTaken) {
		this.timeTaken = timeTaken;
	}
	public ArrayList getQueryResultAsAL() {
		return VSEnablerResponse;
	}
	public void setVSEnablerResponse(ArrayList enablerResponse) {
		VSEnablerResponse = enablerResponse;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
}
