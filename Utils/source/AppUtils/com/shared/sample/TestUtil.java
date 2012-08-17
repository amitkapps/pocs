package com.shared.sample;

import java.util.Enumeration;
import java.util.Iterator;

import org.apache.log4j.Appender;
import org.apache.log4j.Logger;

import com.shared.utils.FileUtil;

public class TestUtil {
	private static Logger log = Logger.getLogger(TestUtil.class);
	public static String greet(){
		log.debug("Sample test called.");
		log.error("Errors will be logged here...");
		StringBuffer sb = new StringBuffer();
		sb.append("CLASSPATH[").append(System.getProperties()).append("]");
		try{
			sb.append(FileUtil.getFileContentsAsAL("log4j.properties"));
			}catch(Exception ex){
				sb.append("ERROR:").append(ex.toString());
			}

		
		Enumeration lenum = Logger.getRootLogger().getAllAppenders();
		while (lenum.hasMoreElements()) {
			Appender a = (Appender) lenum.nextElement();
			sb.append(a.getName()).append(",");
		}
		return "Appenders[" + sb.toString()+ "]";
	}
	
	public static void main(String[] args){
		System.out.println(greet());
	}
}
