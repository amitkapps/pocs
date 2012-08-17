/*
 * $Id: HelloWorld.java,v 1.7 2006/09/14 22:52:47 xlv Exp $
 * $Name:  $
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itextdocs.lowagie.com/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */

package com.learning.itext.sample;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.naming.Context;

import org.jfree.chart.JFreeChart;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.poc.utils.EmailSender;
import com.report.jfreechart.VINSightDashboardReport;

/**
 * Generates a simple 'Hello World' PDF file.
 * 
 * @author blowagie
 */

public class DashBoardPDF {

	/**
	 * Generates a PDF file with the text 'Hello World'
	 * 
	 * @param args no arguments needed here
	 */
	public static byte[] getDashboardPDFAsByteArray() {

		String localProviderURL = "t3://localhost:7001";
		System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		System.setProperty(Context.PROVIDER_URL, localProviderURL);
		
		
		System.out.println("Generating VINSight DashBoard Reports");

		
		
		
		// step 1: creation of a document-object
		Document document = new Document();

		ByteArrayOutputStream baos = new ByteArrayOutputStream(100000);

    	try {
    		// step 2
    		PdfWriter writer;
			writer = PdfWriter.getInstance(document, baos);
			// step 3
    		document.open();
			// step 4: we add a paragraph to the document
			document.add(new Paragraph("VINSight Health Check"));
			document.add(new Paragraph("Report 1"));

			//create the chart1 image
			JFreeChart  chart1 = VINSightDashboardReport.createVINSightDashBoardChart1();
			BufferedImage image1 = chart1.createBufferedImage(350, 350);
			image1.flush();

			document.add(com.lowagie.text.Image.getInstance(image1, null) );
			document.add(new Paragraph("Report 2"));
			JFreeChart  chart2 = VINSightDashboardReport.createVINSightDashBoardChart2();
			BufferedImage image2 = chart2.createBufferedImage(350, 350);
			image2.flush();

			document.add(com.lowagie.text.Image.getInstance(image2, null) );
    		
		
    	}
    	catch(DocumentException de) {
    		de.printStackTrace();
    	}catch(Exception e){
    		e.printStackTrace();
    	}finally{
    	// step 5
    		document.close();
   			try{ baos.close();}catch(IOException ioe){/*ignore*/}
    	}
    	
    	return baos.toByteArray();
		
	}
	
	
	public static void main(String[] args) {
		try{
			byte[] pdfByteArr = getDashboardPDFAsByteArray();
			EmailSender es = new EmailSender("akapoor@matson.com");
			es.setPlainTextMessage("VINSight Dashboard report attached");
			es.setHtmlMessage("<ul>Attached-<li>Dashboard report <li>Sample Message<ul>");
			es.setSubject("VINSight: Dashboard Report");
			es.setFromEmailAddress("akapoor@matson.com");
			es.setReplyToEmailAddress("akapoor@matson.com, amit.kapps@gmail.com");
			es.addInMemoryBinaryAttachment("VINSight DashBoard Report.pdf", pdfByteArr, es.CONTENT_TYPE_PDF);
			es.addInMemoryHtmlTextAttachment("hello.txt", "Hello this is amit.");
			es.sendMail();
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		
	}
}