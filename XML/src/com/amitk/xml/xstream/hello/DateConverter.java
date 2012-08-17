package com.amitk.xml.xstream.hello;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateConverter implements Converter {

	private static String dateFormat = "MMM/dd/yyyy hh:mm:ss";
	private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
	
	public boolean canConvert(Class clazz) {
		return Date.class.equals(clazz);
	}
	
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Date dateSource = (Date) source; 
		writer.addAttribute("format", dateFormat);
		writer.setValue(simpleDateFormat.format(dateSource));
	}
	
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) 
	{
		String thisDateFormat = dateFormat;
		SimpleDateFormat thisSimpleDateFormat = simpleDateFormat; 

		String tempDateFormat = reader.getAttribute("format");
		if(null != tempDateFormat && !"".equals(tempDateFormat.trim())){
			thisDateFormat = tempDateFormat;
			thisSimpleDateFormat = new SimpleDateFormat(thisDateFormat);
		}
		
		String dateValue = reader.getValue();
		Date date = null;
		if(null != dateValue && !"".equals(dateValue.trim()) ){
			try {
				date = thisSimpleDateFormat.parse(dateValue);
			} catch (Exception e) {
				throw new RuntimeException("Exception parsing date field!!", e);
			}
		}
			
		return date;
	}
	
}
