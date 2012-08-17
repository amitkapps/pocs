package com.report.struts.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionServlet;

public class ReportActionServlet extends ActionServlet {

	Logger log = Logger.getLogger(ReportActionServlet.class);
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("Initiating Servlet Request");
		super.doGet(request, response);
		log.info("Processed Servlet Request");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		log.info("Initiating Servlet Request");
		super.doPost(request, response);
		log.info("Processed Servlet Request");
	}
}
