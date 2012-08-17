package com.milops.test.analyze;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import com.shared.utils.FileUtil;

public class TimeSeriesChart {
	private static final String 
		baseDir = "C:\\AmitK\\Other\\MIL1.0-PerfTesting\\Analysis\\scripts\\";

	public static void main(String[] args) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

			String svr1StartTime = "02/12/2008 22:55:47"; //SVR1 start time
				
				String transDataFilePath = baseDir + "access\\Prod\\filter_feb15_svr1_times_below20.log";
				TimeSeries transTimeSeries = buildDataSet(transDataFilePath);
				
				String gcDataFilePath = baseDir + "gc\\gcLogs_svr1_feb15.log";
				TimeSeries gcTimeSeries = buildGCTimesDataSet(gcDataFilePath, sdf.parse(svr1StartTime));
				TimeSeriesCollection tsc = new TimeSeriesCollection();
				tsc.addSeries(gcTimeSeries);
				tsc.addSeries(transTimeSeries);
				generateChart(tsc, "Feb 15 SVR2 Trans/GC map", "Feb_15_SVR1_Transaction_times_GC_Times_map.jpeg", 17000, 800);
//Server 2
				String svr2StartTime = "02/12/2008 23:04:24"; //SVR2 start time

				
				String transDataFilePathSvr2 = baseDir + "access\\Prod\\filter_feb15_svr2_times_below20.log";
				TimeSeries transTimeSeriesSvr2 = buildDataSet(transDataFilePathSvr2);
				
				String gcDataFilePathSvr2 = baseDir + "gc\\gcLogs_svr2_feb15.log";
				TimeSeries gcTimeSeriesSvr2 = buildGCTimesDataSet(gcDataFilePathSvr2, sdf.parse(svr2StartTime));
				TimeSeriesCollection tscSvr2 = new TimeSeriesCollection();
				tscSvr2.addSeries(gcTimeSeriesSvr2);
				tscSvr2.addSeries(transTimeSeriesSvr2);
				generateChart(tscSvr2, "Feb 15 SVR2 Trans/GC map", "Feb_15_SVR2_Transaction_times_GC_Times_map.jpeg", 17000, 800);

		} catch (Exception ex){
			ex.printStackTrace();
		}
	}

	public static TimeSeries buildGCTimesDataSet(String filePath, Date serverStartTime) throws Exception{
		long serverStartMillis = serverStartTime.getTime();//sdf.parse(start).getTime(); 
		ArrayList dataSet = FileUtil.getFileContentsAsAL(filePath);

		ArrayList numRows = new ArrayList(dataSet.size());
		
		// Create a time series chart
		String startLogTime = ((String)dataSet.get(0)).split("\\|")[0];
		String endLogTime = ((String)dataSet.get(dataSet.size() -1)).split("\\|")[0];
		StringBuffer fromToTime = new StringBuffer();
		fromToTime.append("[")
				.append(startLogTime)
				.append(" To ")
				.append(endLogTime).append("]");
		TimeSeries transTimeSeries = new TimeSeries("Garbage Collections" + fromToTime.toString(), Millisecond.class);
		
		//Start building the dataset
		for (Iterator iter = dataSet.iterator(); iter.hasNext();) {
			String[] timeStat = ((String) iter.next()).split("\\|");
			String logTimeSecs = timeStat[0];
			String gcTimeSecs = timeStat[1];

			long logTime = (long)(Float.parseFloat(logTimeSecs)*1000);
			Date logDateb4 = new Date(serverStartMillis + logTime - 1);
			Date logDateStart = new Date(serverStartMillis + logTime);

			float gcTimeMillis = (Float.parseFloat(gcTimeSecs)) * 1000;
			
			Date logDateEnd = new Date(serverStartMillis + logTime + (long)gcTimeMillis);

			Date logDateAfter = new Date(serverStartMillis + logTime + (long)gcTimeMillis + 1);
			
			transTimeSeries.add(new Millisecond(logDateb4), 0);
			transTimeSeries.add(new Millisecond(logDateStart), 20);
			transTimeSeries.add(new Millisecond(logDateEnd),20);
			transTimeSeries.add(new Millisecond(logDateAfter), 0);
		}
		return transTimeSeries;		
		
		
	}
	
	
	public static TimeSeries buildDataSet(String filePath) throws Exception{
		//ArrayList dataSet = new ArrayList();
		ArrayList dataSet = FileUtil.getFileContentsAsAL(filePath);
		//LogUtil.logContentsOnEachLine(dataSet, "Server Stats");
		ArrayList numRows = new ArrayList(dataSet.size());
		
		// Create a time series chart
		String startLogTime = ((String)dataSet.get(0)).split("\\|")[0];
		String endLogTime = ((String)dataSet.get(dataSet.size() -1)).split("\\|")[0];
		StringBuffer fromToTime = new StringBuffer();
		fromToTime.append("[")
				.append(startLogTime)
				.append(" To ")
				.append(endLogTime).append("]");
		TimeSeries transTimeSeries = new TimeSeries("MIL Transactions" + fromToTime.toString(), Millisecond.class);
		String entry = null;
		//Start building the dataset
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss,SSS");
		for (Iterator iter = dataSet.iterator(); iter.hasNext();) {
			entry = (String) iter.next();
			String[] timeStat = entry.split("\\|");
			String logTime = timeStat[0];
			
			Date logDate = sdf.parse(logTime);
			String transTime = timeStat[1];
			try{
				transTimeSeries.add(new Millisecond(logDate), Float.parseFloat(transTime));
			}catch(Exception e){
				try {
					logDate = new Date(logDate.getTime() + 1);
					transTimeSeries.add(new Millisecond(logDate), Float.parseFloat(transTime));
				} catch (Exception e2) {
					System.out.println("Error adding," + entry );
				}
				
			}
		}
		//TimeSeriesCollection tsc = new TimeSeriesCollection();
		//tsc.addSeries(transTimeSeries);
		return transTimeSeries;		
	}
	
	public static void generateChart(TimeSeriesCollection timeSeriesCollection, String title, String chartFileName,
			int length , int breadth) throws Exception{
		
		//TimeSeriesCollection dataset = new TimeSeriesCollection();
		//dataset.addSeries(timeSeries);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				title, "Time: ", "Transaction Times", timeSeriesCollection,
				true, true, true);
		((DateAxis)chart.getXYPlot().getDomainAxis()).setDateFormatOverride(new SimpleDateFormat("HH:mm:ss"));
		((NumberAxis)chart.getXYPlot().getRangeAxis()).setAutoRangeIncludesZero(true);
		String chartFilePath = baseDir + "graphs\\" + chartFileName;
		try {
			ChartUtilities.saveChartAsJPEG(new File(chartFilePath), chart,
					length, breadth);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}
	

}
