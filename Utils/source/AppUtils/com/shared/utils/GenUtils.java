package com.shared.utils;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class GenUtils {
	private static Logger logger = Logger.getLogger(GenUtils.class);
	public static String getQueryForRow(String colNames, String colValues, String delim, String appInstance){
		
		logger.debug("ColNames[" + colNames + "]\n, ColValues[" + colValues +"]");
		
		String updateSubjectSQL = null;
		if ("PROD".equalsIgnoreCase(appInstance)) {
			updateSubjectSQL = "Update pdb2.master ";
		} 
		else if ("DEV".equalsIgnoreCase(appInstance)) {
			updateSubjectSQL = "Update idb2.master ";
		}
		StringBuffer setClause = new StringBuffer(" set ");
		StringBuffer whereClause = new StringBuffer(" where ");

		StringTokenizer colNameTkn = new StringTokenizer(colNames, delim);
		StringTokenizer colValueTkn = new StringTokenizer(colValues, delim);
		String colName = null;
		String colValue = null;
		boolean isWhr = false;
		boolean isSet = false;
		String andWhere = null;
		String andSet = null;
		
		while (colNameTkn.hasMoreTokens()){
			colName = colNameTkn.nextToken();
			colValue = colValueTkn.nextToken();
			
			if (colName.startsWith("WHR_")) {
				colName = colName.substring(4);
				andWhere = isWhr ? " and " : "";
				if (colName.equalsIgnoreCase("VEH_LOAD_PORT")) {
					whereClause.append(andWhere)
								.append(getLoadPortWhereClause(colName, colValue));
				} else {whereClause.append(andWhere + colName + " = '" + colValue + "'"); }
				isWhr = true;
			} else if (colName.startsWith("UPD_")) {
				colName = colName.substring(4);
				andSet = isSet ? ", " : "";
				setClause.append(andSet + colName + " = '" + colValue + "'");
				isSet = true;
			} 
		}
		String updQuery = updateSubjectSQL + setClause.toString() + whereClause.toString() + "\n";
		return updQuery;
	}
	
	
	
	private static String getLoadPortWhereClause(String colName, String colValue){
		String loadPortWhereClause = null;

		if ("HON".equalsIgnoreCase(colValue)) {
			loadPortWhereClause = " " + colName + " in ('HON','HONO') ";
		} else if ("LAX".equalsIgnoreCase(colValue)) {
			loadPortWhereClause = " " + colName + " in ('LA','LAX') ";
		} else if ("KAH".equalsIgnoreCase(colValue)) {
			loadPortWhereClause = " " + colName + " in ('KAH','KAHU') ";
		} else if ("HIL".equalsIgnoreCase(colValue)) {
			loadPortWhereClause = " " + colName + " in ('HIL','HILO') ";
		} else 
		{
			loadPortWhereClause = " " + colName + " = '" + colValue + "' ";
		}
		return loadPortWhereClause;
	}
	
	
	public static String getRowsAsHtmlTable(ArrayList rows){
		String rowsAsHtml = "<b>NO. OF ROWS RETURNED =[0]</b><br />";
		StringBuffer rowsAsHtmlBuf = new StringBuffer ("<table border=\"1\" >");

		if (null!=rows && rows.size()>0) {
			
			Iterator rowsIter = rows.iterator();
			ArrayList colNames;
	
			if (rowsIter.hasNext()) {
			   colNames = (ArrayList)rowsIter.next();
			   //build header
			   rowsAsHtmlBuf.append("<thead><tr><th>SNo</th>");
			   for(int i=0; i<colNames.size() ; i++){
				   rowsAsHtmlBuf.append("<th>" + colNames.get(i) + "</th>");
			   }
			   rowsAsHtmlBuf.append("</tr></thead>");
			   
			   //build table body
			   rowsAsHtmlBuf.append("<tbody>");
			   Hashtable rec;
			   int listItem=0;
			   while(rowsIter.hasNext()){
					listItem++ ;
					rec = (Hashtable)rowsIter.next();
					rowsAsHtmlBuf.append("<tr><td>"+listItem+"</td>");
	
					for(int i=0 ; i < colNames.size() ; i++){
						String colValue = (String)rec.get(colNames.get(i));
						rowsAsHtmlBuf.append("<td>"+colValue+"</td>");
					}
					rowsAsHtmlBuf.append("</tr>");
				}
			}
		   rowsAsHtmlBuf.append("</tbody></table>");
		   rowsAsHtmlBuf.insert(0,"<b>NO. OF ROWS RETURNED =[" + (rows.size() - 1) + "]</b><br />");
		   rowsAsHtml = rowsAsHtmlBuf.toString();
		}
		logger.debug("Rows As HTML- \n" + rowsAsHtml);
		return rowsAsHtml;
	}
	
	
	
	public static String getQueryResultsAsHtmlTable(ArrayList queryResults){
		String[] queryResult = null;
		StringBuffer queryResultHtmlBuf = new StringBuffer(""); 
		String queryResultHtml;
		boolean gotResults = false;
		
		if (null!=queryResults && queryResults.size()>0){
			gotResults = true;
			String sNo = null;
			String time=null;
			String status= null;
			String result=null;
			String query=null;
			queryResult = null;
			int successCounter=0;
			int oneUpdCounter=0;
			int zeroUpdCounter=0;
			int failureCounter=0;
			queryResultHtmlBuf.append("<table border=\"1\" style=\"font-size: 11\"> <tr><b> <td>SNo</td> <td>Time</td> <td>Status</td> <td>Result</td> <td>Query</td> </b></tr>");
			
			for(int i=0; i<queryResults.size();i++){
				queryResult = (String[])queryResults.get(i);
				sNo    = "<td>" + (i + 1) + "</td>";
				time   = "<td>" + queryResult[0] + "</td>";
				status = "<td>" +  queryResult[1] + "</td>";
				result = "<td><textarea rows=\"4\" cols=\"60\" style=\"font-size: 9\">" + queryResult[2] + "</textarea></td>";
				query  = "<td>" + queryResult[3] + "</td>";
				
				successCounter = (!"<b>FAIL!</b>".equalsIgnoreCase(queryResult[1]))? (successCounter + 1) : successCounter;
				failureCounter = ("<b>FAIL!</b>".equalsIgnoreCase(queryResult[1]))? (failureCounter + 1) : failureCounter;
				oneUpdCounter = ("success".equalsIgnoreCase(queryResult[1]))? (oneUpdCounter + 1) : oneUpdCounter;
				zeroUpdCounter = ("0 updates".equalsIgnoreCase(queryResult[1]))? (zeroUpdCounter + 1) : zeroUpdCounter;
				
				queryResultHtmlBuf.append("<tr>" + sNo + time + status + result + query + "</tr>");
			}
			queryResultHtmlBuf.append("</table>");
			queryResultHtmlBuf.insert(0,"[" + successCounter + "] Of [" + queryResults.size() + "] Queries Executed successfully<br/> ["+oneUpdCounter+"]one Update<br />["+zeroUpdCounter+"]zero Updates<br />["+failureCounter+"]FAILED<br />");
		}
		if (gotResults)	
			queryResultHtml = queryResultHtmlBuf.toString();
		else 
			queryResultHtml="<table border=\"1\"><tr><b> <td>Time</td> <td>Result</td> <td>Query</td> </b></tr> </table> <br/> <b>No Results To Display";
		
		logger.debug("Query Results as HTML-\n" + queryResultHtml);
		return queryResultHtml;
	}
	
	
	public static final String[] getTokensFromString(String string, String tknDelimiter)
													throws Exception{
		StringTokenizer tkns;
		if (null == string)
			throw new Exception("Null String passed.");
		if (null==tknDelimiter)
			tkns = new StringTokenizer(string);
		else 
			tkns = new StringTokenizer(string, tknDelimiter);
		String[] tknArr = new String[tkns.countTokens()];
		
		for (int i = 0; i < tknArr.length; i++) {
			tknArr[i] = tkns.nextToken();
		}
		
		return tknArr;
	}
	
	/**
	 * convert array to a character delimited string
	 * @param fields
	 * @param delimiter
	 * @return
	 */
	public static final String getCharDelimitedStr(String[] fields, String delimiter){
		StringBuffer fieldBuf = new StringBuffer();
		for (int fldCtr = 0; fldCtr < fields.length; fldCtr++) {
			if (fldCtr != 0)
				fieldBuf.append(delimiter);
			
			fieldBuf.append(fields[fldCtr]);
		}
		return fieldBuf.toString();
	}

	/**
	 * convert array to a character delimited string
	 * @param fields
	 * @param delimiter
	 * @return
	 */
	public static final String getCharDelimitedStr(int[] fields, String delimiter){
		StringBuffer fieldBuf = new StringBuffer();
		for (int fldCtr = 0; fldCtr < fields.length; fldCtr++) {
			if (fldCtr != 0)
				fieldBuf.append(delimiter);
			
			fieldBuf.append(fields[fldCtr]);
		}
		return fieldBuf.toString();
	}

}
