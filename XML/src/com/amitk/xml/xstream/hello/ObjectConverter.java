package com.amitk.xml.xstream.hello;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class ObjectConverter implements Converter {

	public boolean canConvert(Class clazz) {
		return true;
	}
		
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		context.convertAnother(source);
	}
	
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context){
		return null;
	}
	
}
