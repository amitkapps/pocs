package com.vinsightutils.enabler.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Appender;

import com.vinsightutils.common.Constants;
import com.shared.utils.ArrayListUtil;

public class UpdateQueryOutputVO implements Serializable{

	private static final long serialVersionUID = 1L;
	private String query = null;
	private long timeTaken = 0;
	private String executionStatus = "";
	ArrayList VSEnablerResponse = null;
	private int rowsUpdated = -1;
	private boolean isCommit;
	
	public UpdateQueryOutputVO( String query,
								long timeTaken,
								String executionStatus,
								ArrayList VSEnablerResponse,
								int rowsUpdated,
								boolean isCommit
								){
		this.query = query;
		this.timeTaken = timeTaken;
		this.executionStatus = executionStatus;
		this.VSEnablerResponse = VSEnablerResponse;
		this.rowsUpdated = rowsUpdated;
		this.isCommit = isCommit;

	}
	
	
	public String getVSEnablerResponseAsString(){
		return ArrayListUtil.getALAsString(VSEnablerResponse, ", ");
	}
	
	public boolean isSuccess(){
		if(Constants.QUERY_STATUS_SUCCESS.equals(this.executionStatus)){
			return true;
		}else return false;
	}
	
	public String getQueryResultAsHTML(){
		StringBuffer htmlBuf = new StringBuffer("<b>QUERY:</b><br />[" + query + "]<br/><b>Status=["+ this.getExecutionStatus() +"]<br/><b>RESULTS:</b><br/>");
		return htmlBuf.toString(); 
	}
	
	public String getExecutionStatus() {
		return executionStatus;
	}
	public void setExecutionStatus(String executionStatus) {
		this.executionStatus = executionStatus;
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
	public int getRowsUpdated() {
		return rowsUpdated;
	}
	public boolean isCommit() {
		return isCommit;
	}


	public void setCommit(boolean isCommit) {
		this.isCommit = isCommit;
	}


	public void setRowsUpdated(int rowsUpdated) {
		this.rowsUpdated = rowsUpdated;
	}
	public String toString(){
		return new StringBuffer()
					.append("[Updated:")
					.append(rowsUpdated)
					.append(", Status:")
					.append(executionStatus)
					.append(", Commited?:")
					.append(isCommit)
					.append(", TimeTaken(millis):")
					.append(timeTaken)
					.append(", response-{")
					.append(getVSEnablerResponseAsString())
					.append("}, Query:(")
					.append(query)
					.append(")]")
				.toString();
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

	public String getIdentifier(){
		StringBuffer identifier = new StringBuffer("");
		String tempQry = query.toUpperCase(); 
		tempQry = tempQry.substring(tempQry.lastIndexOf("WHERE") + 6);
		tempQry = tempQry.replaceAll(" AND ", ",");
		tempQry = tempQry.replaceAll(" ", "");
		identifier.append(tempQry);
		
		return identifier.toString();
	}
	
	public String getSummary(){
		StringBuffer sb = new StringBuffer() 
					.append("[Updated:")
					.append(rowsUpdated)
					.append(", Status:")
					.append(executionStatus)
					.append(", Commited?:")
					.append(isCommit)
					.append(", TimeTaken(millis):")
					.append(timeTaken)
					.append(", Identifier:(")
					.append(getIdentifier())
					.append(")");
				if(!isSuccess()){
					sb.append("\nERROR!!\n")
						.append(getFailureMessageAsString())
						.append("\n");
				}
					sb.append("]");
				return sb.toString();
	}

}
