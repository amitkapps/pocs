package com.poc.utils;

import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

public class EmailUtil {

	private static Logger log = Logger.getLogger(EmailUtil.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		sendSimpleEmail();
	}

	public static void sendSimpleEmail(){
		try{
			SimpleEmail email = new SimpleEmail();
			email.setHostName("mailhost");
			email.addTo("akapoor@matson.com");
			email.setFrom("amit.kapps@gmail.com", "VINSight Application");
			email.setSubject("Test message");
			email.setMsg("This is a simple test of commons-email");
			email.send();		
			
		}catch(Exception e){
			log.error("Exception sending email", e);
		}
		
	}
}
