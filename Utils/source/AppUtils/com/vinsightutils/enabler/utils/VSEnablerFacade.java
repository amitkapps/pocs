package com.vinsightutils.enabler.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.shared.utils.ArrayListUtil;
import com.shared.utils.HttpRequestUtil;
import com.vinsightutils.common.Constants;

/**
 *
 * @author AmitK
 */
public class VSEnablerFacade {
	private static Logger logger = Logger.getLogger(VSEnablerFacade.class);

/**
 * 	Method Parameters-
 *	sqlFilePath - path of SQL file. - each sql on a separate single line.
 *	do commit - whether to commit or not
 *	appInstane - instance of the application to use - DEV for development, PROD for production.
 *	Return- 
 *	returns and ArrayList of String[2] elements.
 *	element[0] = <time taken for executing the Query in millis>
 *	element[1] = <Status of query execution> success/FAIL! 
 *	element[2] = <response received from the VS Enable servlet>
 *	element[3] = <the query that was fired>
*/	
	public static UpdateQueryOutputVOList executeUpdate(String sqlFilePath, boolean isCommit,
			String appInstance) throws Exception {
		logger.info("Executing queries from file-[" + sqlFilePath + "]");
		UpdateQueryOutputVOList updQryVOs= new UpdateQueryOutputVOList();
		try {
			logger.info("Executing Queries with commit=" + isCommit);
			BufferedReader in = new BufferedReader(new FileReader(new File(
					sqlFilePath)));

			String query = null;
			int counter = 0;

			while ((query = in.readLine()) != null) {
				logger.info("Executin update qry [" + counter++ + "]");
				long startTime = System.currentTimeMillis();
				ArrayList response = postUpdateNReturnResult(query, isCommit, appInstance);
				long endTime = System.currentTimeMillis();
				long timeTaken = endTime - startTime;
				
				UpdateQueryOutputVO updQryVO = VSEnablerFacadeHelper.parseUpdQryResponse(query, timeTaken, response, isCommit);
				updQryVOs.add(updQryVO);
			}
			logger.info("Finished executing queries");
		} catch (Exception e) {
			logger.error("Exception", e);
			throw e;
		}

		return updQryVOs;
	}

	/**
	 * Run the update query on the supplied instance.
	 * 
	 * @param query - the query to run
	 * @param commit - true/false, whether you wanna commit or not.
	 * @param appInstance - DEV/PROD   
	 * @return	- an arraylist of response string from the VS Enabler application
	 * @throws Exception
	 */
	public static ArrayList postUpdateNReturnResult(String query,
			boolean commit, String appInstance) throws Exception {
		
		ArrayList response = new ArrayList();

		StringBuffer sb = new StringBuffer();
		sb.append("qKey=3760565290974525751");
		sb.append("&");
		//sb.append("environment=jdbc/vinsDS");
		sb.append("environment=jdbc/mfDS");
		sb.append("&");
		sb.append("qType=U");
		sb.append("&");
		sb.append("qCommit=");
		sb.append(commit ? "Y" : "N");
		sb.append("&");
		sb.append("qSep=T");
		sb.append("&");
		sb.append("qText=");
		sb.append(query);

		logger.debug("Executing Query: " + query);

		try {
			//production URL
			URL url = null;
			//for development
			if ("DEV".equalsIgnoreCase(appInstance))
				url = new URL(Constants.DEV_URL_STRING);
			//for Production
			if ("PROD".equalsIgnoreCase(appInstance))
				url = new URL(Constants.PROD_URL_STRING);

			logger.debug("POSTING QUERY TO Instance-[" + appInstance
					+ "]\nURL=" + url.toString() );
			URLConnection urlConn = url.openConnection();
			DataOutputStream printout;

			// Let the run-time system (RTS) know that we want input.
			urlConn.setDoInput(true);
			// Let the RTS know that we want to do output.
			urlConn.setDoOutput(true);
			// No caching, we want the real thing.
			urlConn.setUseCaches(false);
			// Specify the content type.
			urlConn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			// Send POST output.
			printout = new DataOutputStream(urlConn.getOutputStream());
			printout.writeBytes(sb.toString());
			printout.flush();
			printout.close();

			DataInputStream input = new DataInputStream(urlConn
					.getInputStream());
			String temp = null;
			while (null != (temp = input.readLine())) {
					response.add(temp);
			}
			input.close();
		} catch (Exception e) {
			logger.error("Exception trying to execute query thru VS enabler!",
					e);
			throw e;
		}

		return response;
	}

	/*
	 public static String[] postQueryNReturnResult(String query, boolean commit, String appInstance) throws Exception {
	 boolean success = false;
	 final String NO_RESULT= "NO RESULT!!";
	 String str = NO_RESULT;
	 String[] statusArr = new String[2];
	 
	 StringBuffer sb = new StringBuffer();
	 sb.append("qKey=3760565290974525751");
	 sb.append("&");
	 //sb.append("environment=jdbc/vinsDS");
	 sb.append("environment=jdbc/mfDS");
	 sb.append("&");
	 sb.append("qType=U");
	 sb.append("&");
	 sb.append("qCommit=");
	 sb.append(commit ? "Y" : "N");
	 sb.append("&");
	 sb.append("qSep=T");
	 sb.append("&");
	 sb.append("qText=");
	 sb.append(query);
	 
	 logger.debug("Executing Query: " + query);

	 try {
	 //production URL
	 String PROD_URL_STRING = "http://10.201.1.5:9001/AMISPollerStartupServlet/servlets/enable/VINServlet";
	 String DEV_URL_STRING = "http://10.8.7.145:9001/AMISPollerStartupServlet/servlets/enable/VINServlet";
	 URL url = null;
	 //for development
	 if("DEV".equalsIgnoreCase(appInstance)) url = new URL(DEV_URL_STRING);
	 //for Production
	 if ("PROD".equalsIgnoreCase(appInstance)) url = new URL(PROD_URL_STRING);
	 
	 logger.debug("POSTING QUERY TO URL-["+url.toString()+"]\nInstance="+appInstance);
	 URLConnection urlConn = url.openConnection();
	 DataOutputStream printout;
	 
	 // Let the run-time system (RTS) know that we want input.
	 urlConn.setDoInput(true);
	 // Let the RTS know that we want to do output.
	 urlConn.setDoOutput(true);
	 // No caching, we want the real thing.
	 urlConn.setUseCaches(false);
	 // Specify the content type.
	 urlConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	 
	 // Send POST output.
	 printout = new DataOutputStream(urlConn.getOutputStream());
	 printout.writeBytes(sb.toString());
	 printout.flush();
	 printout.close();
	 
	 DataInputStream input = new DataInputStream (urlConn.getInputStream ());
	 String temp= null;
	 boolean isSuccess = false;
	 String status = "<b>FAIL!</b>";
	 StringBuffer queryOutput = new StringBuffer("");
	 while (null != (temp=input.readLine())) {
	 str = temp;
	 if (null != str) {
	 queryOutput.append(str);
	 if (str.endsWith("ed: 1")){
	 isSuccess = true;
	 status="success";
	 }
	 if (str.endsWith("ed: 0")){
	 isSuccess = true;
	 status="0 updates";
	 }
	 
	 }
	 }
	 if (isSuccess){
	 logger.debug("Query Execution Success");
	 }
	 else logger.error("Error Executing Query-\n[" + query + "]\nError=[" + queryOutput.toString() + "]" );

	 input.close ();
	 if (!NO_RESULT.equalsIgnoreCase(str)) str= queryOutput.toString();
	 statusArr[0] = status;
	 statusArr[1] = str;
	 } catch (Exception e) {
	 logger.error("Exception trying to execute query thru VS enabler!", e);
	 throw e;
	 }

	 return statusArr;
	 }

	 */

	/**
	 * Posts multiple Select queries (Select/Update) defined by Constants.QUERY_TYPE_SEL/QUERY_TYPE_UPD
	 * and returns the output from the VINSight Enable Servlet as ArrayList of Strings.
	 * 
	 * @param sqls - ArrayList of sqls, - each element is a string sql.
	 * @param appInstance - instance of the application to use - DEV for development, PROD for production.
	 * @param environment - AMIS or VS - values AMIS/VS.
	 *  
	 * @return an ArrayList of SelectQueryOuputVO objects.
	 */
	public static SelectQueryOutputVOList executeSelectQuery(
			String appInstance, String environment, ArrayList sqls)
			throws Exception {
		logger.info("Executing the select queries");
		SelectQueryOutputVOList sqlQryOutputVOs = new SelectQueryOutputVOList();
		try {

			String query = null;

			for (int qryCtr = 0; qryCtr < sqls.size(); qryCtr++) {
				logger.debug("Executing Select query - [" + (qryCtr + 1) + "]");
				query = (String) sqls.get(qryCtr);
				long start = System.currentTimeMillis();
				ArrayList execOutput = postQueryNReturnResult(appInstance,
						environment, Constants.QUERY_TYPE_SEL, false,
						query);
				long end = System.currentTimeMillis();
				long timeTaken = end - start;
				SelectQueryOutputVO selQryOuputVO = VSEnablerFacadeHelper
						.parseSelQryResponse(query, timeTaken, execOutput);
				sqlQryOutputVOs.add(selQryOuputVO);
				//logger.info("Executed Query- [" + (++counter) + "] Status= [" + execOutput[0] + "]");
			}
			logger.info("Finished executing queries");
		} catch (Exception e) {
			logger.error("Exception", e);
			throw e;
		}

		return sqlQryOutputVOs;
	}

	/**
	 * Posts a query (Select/Update) and returns the output from the VINSight Enable Servlet as ArrayList of Strings.
	 *
	 * @param environment - either of Constants.APP_ENV_VS / Constants.APP_ENV_AMIS depending what env to fire the query
	 * @param queryType - query type - update or a select query - Constants.QUERY_TYPE_SEL/QUERY_TYPE_UPD
	 * @param isCommit - in case of update query - whether to commit or not.
	 * @param query - the SQL query to be fired.
	 * 
	 * @return ArrayList - the output returned from the VS Enabler servlet, each element is a line read from the servlet response.
	 */
	public static ArrayList postQueryNReturnResult(String appInstance,
			String environment, String queryType, boolean isCommit, String query)
			throws Exception {


		String urlQueryString = getURLQueryString(environment, queryType, isCommit, query);
		String urlString = Constants.getURLStringForInstance(appInstance);
		ArrayList response = HttpRequestUtil.postDataToURL(urlString, urlQueryString);

		return response;

	}

	/**
	 * The message is automatically encoded for posting onto URL
	 * @param appInstance dev or prod
	 * @param message text message
	 * @param isIncomingMessage is to be posted on VINSight Q then true, if acets then false
	 * @return the return result from the VSEnabler
	 * @throws Exception
	 */
	public static String postMessageNReturnResult(String appInstance, String message, boolean isIncomingMessage)
			throws Exception {

		StringBuffer sb = new StringBuffer();
		sb.append("qKey=3760565290974525751");
		sb.append("&");
		sb.append("environment=");
		sb.append(Constants.getMessageEnvironment(appInstance));
		sb.append("&");
		sb.append("qType=M");
		sb.append("&");
		sb.append("qCommit=").append(isIncomingMessage ? "Y" : "N");
		sb.append("&");
		sb.append("qText=");
		sb.append( HttpRequestUtil.encodeURLData(message) );

		//logger.info("Posting Message on "+ (isIncomingMessage ? "VINSight Q" : "ACETS Q") + ", Server:" + appInstance + ", msg: " + message);

		String urlString = Constants.getURLStringForInstance(appInstance);
		String urlQueryString = sb.toString();
		
		return ArrayListUtil.getALAsString(HttpRequestUtil.postDataToURL(urlString, urlQueryString), ",");
	}
	
	

	/**
	 * Builds a query string to be posted to the Vinsight Enabler URL.
	 * The query is automatically encoded here itself.
	 * 
	 * @param appEnvironment
	 * @param queryType
	 * @param isCommit
	 * @param query
	 * @return
	 * @throws Exception
	 */
	private static String getURLQueryString(	String appEnvironment, 
											String queryType, 
											boolean isCommit, 
											String query)
		throws Exception{
		
		StringBuffer sb = new StringBuffer();
		sb.append("qKey=3760565290974525751");
		sb.append("&environment=");
		sb.append(Constants.getQueryEnvironment(appEnvironment));
		sb.append("&qType=");
		sb.append(Constants.getQueryType(queryType));
		sb.append("&");
		sb.append("qCommit=");
		sb.append(isCommit ? "Y" : "N");
		sb.append("&");
		sb.append("qSep=");
		sb.append("&");
		sb.append("qText=");
		sb.append(HttpRequestUtil.encodeURLData(query));
		
		return sb.toString();
	};
}
