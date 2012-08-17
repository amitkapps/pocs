package com.vinsightutils.common;

public class Constants {
	
	public static final String APP_ENV_MF = "jdbc/mfDS";
	public static final String APP_ENV_VS = "jdbc/vinsDS";
	public static final String APP_ENV_MESSAGE_QUEUE = "t3://localhost:9001";
	public static final String APP_ENV_LOCAL_MESSAGE_QUEUE = "t3://localhost:7001";

	public static final String QUERY_TYPE_UPD = "U";
	public static final String QUERY_TYPE_SEL = "Q";

	public static final String APP_INSTANCE_LOCAL = "LOCAL";
	public static final String APP_INSTANCE_DEV = "DEV";
	public static final String APP_INSTANCE_QA = "QA";
	public static final String APP_INSTANCE_PREPROD = "PREPROD";
	public static final String APP_INSTANCE_PROD = "PROD";

	public static final String LOCAL_URL_STRING = 	"http://localhost:7001/AMISPollerStartupServlet/servlets/enable/VINServlet";
	public static final String DEV_URL_STRING = 	"http://10.8.7.145:9001/AMISPollerStartupServlet/servlets/enable/VINServlet";
	public static final String QA_URL_STRING = 		"http://10.201.0.6:9001/AMISPollerStartupServlet/servlets/enable/VINServlet";
	public static final String PREPROD_URL_STRING = "http://10.201.2.7:9001/AMISPollerStartupServlet/servlets/enable/VINServlet";
	public static final String PROD_URL_STRING = 	"http://10.201.1.5:9001/AMISPollerStartupServlet/servlets/enable/VINServlet";
	
	public static final String QUERY_STATUS_SUCCESS = "success";
	public static final String QUERY_STATUS_FAILURE = "FAILED";


	public static final String getURLStringForInstance(String appInstance)
		throws Exception{
		if(null == appInstance)
			throw new Exception ("appInstance passed is null!");
		else if(APP_INSTANCE_LOCAL.equalsIgnoreCase(appInstance))
			return LOCAL_URL_STRING;
		else if(APP_INSTANCE_DEV.equalsIgnoreCase(appInstance))
			return DEV_URL_STRING;
		else if(APP_INSTANCE_QA.equalsIgnoreCase(appInstance))
			return QA_URL_STRING;
		else if(APP_INSTANCE_PREPROD.equalsIgnoreCase(appInstance))
			return PREPROD_URL_STRING;
		else if(APP_INSTANCE_PROD.equalsIgnoreCase(appInstance))
			return PROD_URL_STRING;
		else 
			throw new Exception("Invalid appInstance passed-" + appInstance + ".");
	}
	
	public static final String getQueryType(String queryType)
		throws Exception{
		if(null == queryType)
			throw new Exception ("queryType passed is null!");
		else if (QUERY_TYPE_SEL.equalsIgnoreCase(queryType))
			return QUERY_TYPE_SEL;
		else if (QUERY_TYPE_UPD.equalsIgnoreCase(queryType))
			return QUERY_TYPE_UPD;
		else
			throw new Exception("Incorrect Query Type Passed.");

	}
	
	public static final String getQueryEnvironment(String environment)
		throws Exception{
		if(null == environment)
			throw new Exception ("query environment passed is null!");
		else if (APP_ENV_MF.equalsIgnoreCase(environment))
			return APP_ENV_MF;
		else if (APP_ENV_VS.equalsIgnoreCase(environment))
			return APP_ENV_VS;
		else
			throw new Exception("Incorrect Query environment Passed." + environment);
		
	}

	public static final String getMessageEnvironment(String appInstance)
	throws Exception{
	if(null == appInstance)
		throw new Exception ("appInstance passed is null!");
	else if (APP_INSTANCE_LOCAL.equalsIgnoreCase(appInstance))
		return APP_ENV_LOCAL_MESSAGE_QUEUE;
	else if (	 APP_INSTANCE_DEV.equalsIgnoreCase(appInstance)
			  || APP_INSTANCE_QA.equalsIgnoreCase(appInstance)
			  || APP_INSTANCE_PREPROD.equalsIgnoreCase(appInstance)
			  || APP_INSTANCE_PROD.equalsIgnoreCase(appInstance)
			)
		return APP_ENV_MESSAGE_QUEUE;
	else
		throw new Exception("Incorrect appInstance Passed:" + appInstance);
	
}


}

