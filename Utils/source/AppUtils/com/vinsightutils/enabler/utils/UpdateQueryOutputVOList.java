package com.vinsightutils.enabler.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class UpdateQueryOutputVOList implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList updateQueryOutputVOList = new ArrayList();
	int successCount = 0;
	int failureCount = 0;
	int zeroUpdateCount = 0;
	int noCommitCounts = 0;
	
	public void add(UpdateQueryOutputVO updateQueryOutputVO){
		updateQueryOutputVOList.add(updateQueryOutputVO);
		if(updateQueryOutputVO.isSuccess())
			successCount++;
		else
			failureCount++;
		if(updateQueryOutputVO.getRowsUpdated() == 0)
			zeroUpdateCount++;
		
		if(!updateQueryOutputVO.isCommit())
			noCommitCounts++;
	}
	
	public UpdateQueryOutputVO get(int i){
		return (UpdateQueryOutputVO)updateQueryOutputVOList.get(i);
	}
	
	public int size(){
		return updateQueryOutputVOList.size();
	}

	public String getResultsAsHTMLTable(){
		StringBuffer htmlBuf = new StringBuffer();
		htmlBuf.append("<table ><tr><th>not implemented</th></tr></table>");
		//String tableHead= "<tr><th>SNo</th><th>Count</th><th>status</th><th>Result Table</th><th>Qry result</th><th>Query</th></tr>";
		return htmlBuf.toString();
	}
	
	public Iterator iterator(){
		return updateQueryOutputVOList.iterator();
	}
	
	public int getFailureCount(){
		return failureCount;
	}
	
	public int getSuccessCount(){
		return successCount;
	}
	
	public int getZeroUpdateCount(){
		return zeroUpdateCount;
	}
	
	public String getShortSummary(){
		StringBuffer summary = new StringBuffer("Short Summary:\n");
		summary.append("Success:").append(getSuccessCount())
			.append(",noCommits:").append(noCommitCounts)
			.append(", Failures:").append(getFailureCount())
			.append(", Total:").append(size())
			.append(", ZeroUpdates:").append(zeroUpdateCount)
		;
		return summary.toString();
	}

	public String getRecordWiseLongSummary(){
		StringBuffer longSummary = new StringBuffer("Long Summary:\n");
		for (Iterator iter = updateQueryOutputVOList.iterator(); iter.hasNext();) {
			UpdateQueryOutputVO updQryOPVO = (UpdateQueryOutputVO) iter.next();
			longSummary.append(updQryOPVO.toString());
			if(iter.hasNext())
				longSummary.append("\n");
		}
		
		return longSummary.toString();
	}

	public String getRecordWiseSummary(){
		StringBuffer longSummary = new StringBuffer("");
		for (Iterator iter = updateQueryOutputVOList.iterator(); iter.hasNext();) {
			UpdateQueryOutputVO updQryOPVO = (UpdateQueryOutputVO) iter.next();
			longSummary.append(updQryOPVO.getSummary());
			if(iter.hasNext())
				longSummary.append("\n");
		}
		
		return longSummary.toString();
	}
	
	public String logRecordWiseSummary(Logger logger){
		logger.debug("Long Summary:");
		logger.getRootLogger().getAppender("").getLayout();
		
		StringBuffer longSummary = new StringBuffer("");
		for (Iterator iter = updateQueryOutputVOList.iterator(); iter.hasNext();) {
			UpdateQueryOutputVO updQryOPVO = (UpdateQueryOutputVO) iter.next();
			longSummary.append(updQryOPVO.getSummary());
			if(iter.hasNext())
				longSummary.append("\n");
		}
		
		return longSummary.toString();
	}
	
	
}
