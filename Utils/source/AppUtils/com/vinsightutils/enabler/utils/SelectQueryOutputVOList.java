package com.vinsightutils.enabler.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;


public class SelectQueryOutputVOList implements Serializable{

	private static final long serialVersionUID = 1L;
	private ArrayList selectQueryOutputVOList = new ArrayList();
	
	public void add(SelectQueryOutputVO selectQueryOutputVO){
		selectQueryOutputVOList.add(selectQueryOutputVO);
	}
	
	public SelectQueryOutputVO get(int i){
		return (SelectQueryOutputVO)selectQueryOutputVOList.get(i);
	}
	
	public int size(){
		return selectQueryOutputVOList.size();
	}

	public String getResultsAsHTMLTable(){
		StringBuffer htmlBuf = new StringBuffer();
		htmlBuf.append("<table >");
		String tableHead= "<tr><th>SNo</th><th>Count</th><th>status</th><th>Result Table</th><th>Qry result</th><th>Query</th></tr>";
		htmlBuf.append(tableHead);
		int resultsCtr = 1;
		int successCtr = 0;
		int nonZeroCountsCtr = 0;
		String rowStart="<tr>";
		String rowEnd="</tr>";
		for (Iterator selQryOpAL = selectQueryOutputVOList.iterator(); selQryOpAL.hasNext(); resultsCtr++) {
			rowStart="<tr>";
			SelectQueryOutputVO selQryOpVO = (SelectQueryOutputVO) selQryOpAL.next();
			String SNo = "<td>" + resultsCtr + "</td>";
			int rowCount = selQryOpVO.getGenericQueryResultVO().getRowCount();
			String selResultSize = "";
			selResultSize = "<td>" + rowCount + "</td>";
			if (rowCount != 0)
				rowStart = "<tr class=\"green\" >";
			if (!selQryOpVO.isSuccess())
				rowStart = "<tr class=\"red\" >";
				
				
			
			String status = "<td>" + selQryOpVO.getExecutionStatus() + "</td>";
			String resultTable = "<td>" + selQryOpVO.getGenericQueryResultVO().getRowsAsHTML() + "</td>";
			String result = "<td><textarea rows=\"4\" cols=\"140\" >" 
							+ selQryOpVO.getQueryResultAsString() 
							+ "</td></textarea>";
			String query = "<td><textarea rows=\"4\" cols=\"140\" >" + selQryOpVO.getQuery() + "</textarea></td>";
			htmlBuf.append(rowStart)
					.append(SNo).append(selResultSize).append(status).append(resultTable).append(result).append(query)
					.append(rowEnd);
			
		}
		htmlBuf.append("</table>");
		String summary = "Selects with return results>0- [" + nonZeroCountsCtr +"]<br/>";
		htmlBuf.insert(0,summary);
		
		return htmlBuf.toString();
	}
}
