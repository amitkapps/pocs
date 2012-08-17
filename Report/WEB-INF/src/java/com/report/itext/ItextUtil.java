package com.report.itext;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.jfree.chart.JFreeChart;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;

public class ItextUtil {

	
    /**
     * Converts a JFreeChart to PDF syntax.
     * @param filename	the name of the PDF file
     * @param chart		the JFreeChart
     * @param width		the width of the resulting PDF
     * @param height	the height of the resulting PDF
     */
    public static byte[] writeToStreamAsPDF(JFreeChart chart, int width, int height, ByteArrayOutputStream baos) {
    	// step 1
    	Document document = new Document(new Rectangle(width, height));
    	boolean wasBaosNull = false;
    	if(null == baos){
			baos = new ByteArrayOutputStream(100000);
			wasBaosNull = true;
    	}

    	try {
    		// step 2
    		PdfWriter writer;
			writer = PdfWriter.getInstance(document, baos);
			// step 3
    		document.open();
    		// step 4
    		PdfContentByte cb = writer.getDirectContent();
    		PdfTemplate tp = cb.createTemplate(width, height);
    		Graphics2D g2d = tp.createGraphics(width, height, new DefaultFontMapper());
    		Rectangle2D r2d = new Rectangle2D.Double(0, 0, width, height);
    		chart.draw(g2d, r2d);
    		g2d.dispose();
    		cb.addTemplate(tp, 0, 0);
    	}
    	catch(DocumentException de) {
    		de.printStackTrace();
    	}finally{
    	// step 5
    		document.close();
    		if(wasBaosNull){
    			try{
    				baos.close();
    			}catch(IOException ioe){/*ignore*/}
    		}
    	}
    	
    	return baos.toByteArray();
    }
	
	
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
