package com.amitk.jasper.test;

import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public class HelloJasper {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Testing a sample hello world jasper report
		
		JasperReport jasperReport;
		JasperPrint jasperPrint;
		
		try {
			jasperReport = JasperCompileManager.compileReport("jasper/jrxml/TableOfContentsReport.jr.xml");
			jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), new JREmptyDataSource());
			JasperExportManager.exportReportToPdfFile(jasperPrint, "simpleReport.pdf");
			JasperExportManager.exportReportToHtmlFile(jasperPrint, "simpleReport.html");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
