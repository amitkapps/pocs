package com.report.jfreechart;

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
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class SampleJasper {
	private static Logger log = Logger.getLogger(SampleJasper.class);
	private static final String 
		baseDir = "C:\\AmitK\\work\\project\\Learning\\JavaTools\\JFreeChart\\BuildingCharts\\";

	public static void main(String[] args) {
		try{
				//TimeSeries timeSeries = buildDataSet(dataFilePath);
				//generateChart(timeSeries, title, chartFileName);
			throw new Exception("Under implementation!!");
		} catch (Exception ex){
			log.error(ex);
		}
	}

	public static TimeSeries buildDataSet(String filePath) throws Exception{
		ArrayList dataSet = new ArrayList();//FileUtil.getFileContentsAsAL(filePath);
		//LogUtil.logContentsOnEachLine(dataSet, "Server Stats");
		ArrayList rows = new ArrayList(dataSet.size());
		
		// Create a time series chart
		String startLogTime = ((String)dataSet.get(0));
		startLogTime = startLogTime.substring(0, startLogTime.indexOf("|"));
		String endLogTime = ((String)dataSet.get(dataSet.size() -1));
		endLogTime = endLogTime.substring(0, endLogTime.indexOf("|"));
		StringBuffer fromToTime = new StringBuffer();
		fromToTime.append("[")
				.append(startLogTime)
				.append(" To ")
				.append(endLogTime).append("]");
		TimeSeries srvMem = new TimeSeries("Free Memory(MB)" + fromToTime.toString(), Second.class);
		
		//Start building the dataset
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		for (Iterator iter = dataSet.iterator(); iter.hasNext();) {
			String serverStat = (String) iter.next();
			String logTime = serverStat.substring(0, serverStat.indexOf("|"));
			
			Date logDate = sdf.parse(logTime);
			String mbeanStat = serverStat.substring(serverStat.indexOf("|") + 1);
			//log.debug("Server Time:" + logTime);
			log.debug(mbeanStat);
			String mbeanStatFormatted = mbeanStat.replaceAll("[\\{\\}\\:\\,]", "|").replaceAll("\"","");
			log.debug(mbeanStatFormatted);
			String[] mbeanStatArr = mbeanStatFormatted.split("\\|");
			HashMap fieldVals = new HashMap();
			fieldVals.put("Time", logDate);
			for (int i = 0; i < mbeanStatArr.length; i++) {
				if( mbeanStatArr[i].trim().length() > 0 ){
					String[] props = mbeanStatArr[i].split("=");
					if(props.length == 2){
						String propName = props[0];
						String propValue = props[1];
						fieldVals.put(propName, propValue);
						if("HeapFreeCurrent".equalsIgnoreCase(propName)){
							try{
								srvMem.add(new Second(logDate), Integer.parseInt(propValue)/(1024*1024));
							}catch(Exception ex){log.warn(ex);}
						}
					}
				}
			}
		}
		return srvMem;		
	}
	
	public static void generateChart(TimeSeries timeSeries, String title, String chartFileName) throws Exception{
		
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		dataset.addSeries(timeSeries);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				title, "Time: ", "Free Memory(MB)", dataset,
				true, true, true);
		((DateAxis)chart.getXYPlot().getDomainAxis()).setDateFormatOverride(new SimpleDateFormat("MM/dd HH:mm"));
		((NumberAxis)chart.getXYPlot().getRangeAxis()).setAutoRangeIncludesZero(true);
		String chartFilePath = baseDir + "graphs\\" + chartFileName;
		try {
			ChartUtilities.saveChartAsJPEG(new File(chartFilePath), chart,
					1500, 500);
		} catch (IOException e) {
			System.err.println("Problem occurred creating chart.");
		}
	}
	
    private static CategoryDataset createDataset() {
        
        // row keys...
        String series1 = "First";
        String series2 = "Second";
        String series3 = "Third";

        // column keys...
        String category1 = "Category 1";
        String category2 = "Category 2";
        String category3 = "Category 3";
        String category4 = "Category 4";
        String category5 = "Category 5";

        // create the dataset...
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(1.0, series1, category1);
        dataset.addValue(4.0, series1, category2);
        dataset.addValue(3.0, series1, category3);
        dataset.addValue(5.0, series1, category4);
        dataset.addValue(5.0, series1, category5);

        dataset.addValue(5.0, series2, category1);
        dataset.addValue(7.0, series2, category2);
        dataset.addValue(6.0, series2, category3);
        dataset.addValue(8.0, series2, category4);
        dataset.addValue(4.0, series2, category5);

        dataset.addValue(4.0, series3, category1);
        dataset.addValue(3.0, series3, category2);
        dataset.addValue(2.0, series3, category3);
        dataset.addValue(3.0, series3, category4);
        dataset.addValue(6.0, series3, category5);
        
        return dataset;
        
    }
	
	
	
}
