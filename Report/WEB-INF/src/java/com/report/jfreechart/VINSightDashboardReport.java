/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2006, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU Lesser General Public License as published by 
 * the Free Software Foundation; either version 2.1 of the License, or 
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but 
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public 
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, 
 * USA.  
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ------------------
 * BarChartDemo1.java
 * ------------------
 * (C) Copyright 2003-2006, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   ;
 *
 * $Id: BarChartDemo1.java,v 1.1.2.3 2006/10/25 10:38:47 mungady Exp $
 *
 * Changes
 * -------
 * 09-Mar-2005 : Version 1, copied from the demo collection that ships with
 *               the JFreeChart Developer Guide (DG);
 *
 */

package com.report.jfreechart;

import java.awt.Color;
import java.awt.GradientPaint;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;

import javax.naming.Context;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import com.poc.utils.EmailSender;
import com.report.business.DashboardReportDAO;
import com.report.itext.ItextUtil;

/**
 * A simple demonstration application showing how to create a bar chart.
 */
public class VINSightDashboardReport {

    /**
     * Creates a new demo instance.
     *
     * @param title  the frame title.
     */
    public static JFreeChart createVINSightDashBoardChart1() throws Exception{
        CategoryDataset dataset 
        	= createDefaultCategoryDataset(DashboardReportDAO.listActiveUnitsByLocation(), "Active Units");
        JFreeChart chart = createCategoryChart(dataset,
        										"Units currently being handled",       // chart title
        										"Ports",               // domain axis label
        										"VINSight Unit Count"        // range axis label
        										);
        
        return chart;
        
    }

    public static JFreeChart createVINSightDashBoardChart2() throws Exception{
    	int noOfDaysOld = 100;
        CategoryDataset dataset 
        	= createDefaultCategoryDataset(DashboardReportDAO.listAgingUnitsByLocation(noOfDaysOld), "Aging Units");
        JFreeChart chart = createCategoryChart(	dataset, 
        										"VINSight Units " + noOfDaysOld + " days since receive",       // chart title
        										"Ports",               // domain axis label
        										"VINSight Unit Count"        // range axis label
        									);
        
        return chart;
        
    }
    
    
    /**
     * Returns a sample dataset.
     * 
     * @return The dataset.
     */
    private static CategoryDataset createDefaultCategoryDataset(List list, String footer) {
        
    	//List activeUnitCountForPorts = DashboardReport.listActiveUnitsByLocation();

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    	
    	for (Iterator iter = list.iterator(); iter.hasNext();) {
			Object[] activeCountAndPort = (Object[]) iter.next();
			
			for (int i = 0; i < activeCountAndPort.length; i++) {
				String port = String.valueOf(activeCountAndPort[0]);
				int count = ((Integer) activeCountAndPort[1]).intValue();
				dataset.addValue(count, footer, port);
			}
		}
        return dataset;
        
    }
    
    /**
     * Creates a sample chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private static JFreeChart createCategoryChart( CategoryDataset dataset, 
    											   String chartTitle, 
    											   String domainAxisLable,
    											   String rangeAxisLable
    											 ) {
        
        // create the chart...
        JFreeChart chart = ChartFactory.createBarChart(
        	chartTitle,
        	domainAxisLable,
        	rangeAxisLable,
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.white);

        // get a reference to the plot for further customisation...
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setDomainGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.white);

        // ******************************************************************
        //  More than 150 demo applications are included with the JFreeChart
        //  Developer Guide...for more information, see:
        //
        //  >   http://www.object-refinery.com/jfreechart/guide.html
        //
        // ******************************************************************
        
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(false);
        
        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 
                0.0f, 0.0f, new Color(0, 0, 64));
        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 
                0.0f, 0.0f, new Color(0, 64, 0));
        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 
                0.0f, 0.0f, new Color(64, 0, 0));
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                        Math.PI / 6.0));
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
        
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
     */
    public static void main(String[] args) {
    	try{
			String localProviderURL = "t3://localhost:7001";
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
			System.setProperty(Context.PROVIDER_URL, localProviderURL);
	    	
	        JFreeChart chart1 = VINSightDashboardReport.createVINSightDashBoardChart1();
	        
	        //File chartFile = new File("C:\\home\\appoutput\\jfreechart\\VINSightDashboard.jpeg");
	        //ChartUtilities.saveChartAsJPEG(chartFile, chart, 500, 400);
	        ByteArrayOutputStream baos = new ByteArrayOutputStream(10000);
	        ItextUtil.writeToStreamAsPDF(chart1, 400, 400, baos);
	        EmailSender es = new EmailSender("akapoor@matson.com");
	        es.setSubject("VINSight Dashboard");
	        es.setPlainTextMessage("see attached");
	        es.addInMemoryBinaryAttachment("Dashboard.pdf", baos.toByteArray(), "application/pdf");
	        es.sendMail();

    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

}
