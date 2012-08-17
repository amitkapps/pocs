package com.shared.utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class HttpRequestUtil {
	private static Logger logger = Logger.getLogger(HttpRequestUtil.class);

	public static String encodeURLData(String data) throws Exception{
		return URLEncoder.encode(data, "UTF-8");
	}

	public static ArrayList postDataToURL(String urlString, String urlQueryStringData)
		throws MalformedURLException, IOException, Exception{
		ArrayList response = new ArrayList();
		URL url = new URL(urlString);	
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
		printout.writeBytes(urlQueryStringData);
		printout.flush();
		printout.close();

		DataInputStream input = new DataInputStream (urlConn.getInputStream ());
		
		String readLine = null;
		while (null != (readLine = input.readLine()))
			response.add(readLine);

		input.close();

		return response;
		
	}
	

}
