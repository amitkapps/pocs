package com.amitk.xslt.hello;

import java.io.File;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;


public class TestMandatoryTagsXSLT {

	public static void main(String[] args) {
		
		try {
			transform();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void transform() throws Exception{
		String xmlFilePath = "xml/TestMandatoryTags.xml";
		//String xmlFilePath = "xml/OrderCreate.xml";
		//String xmlFilePath = "xml/OrderStopEvent1.xml";
		//String xmlFilePath = "xml/OrderCreateASNCore.xml";
		//String xmlFilePath = "xml/OrderStopEvent1ASNCore.xml";
		//String xmlFilePath = "xml/Max-testing.xml";
		
		String xslFilePath = "xslt/TestMandatoryTags.xslt";
		//String xslFilePath = "xslt/ASNSplitter.xslt";
		//String xslFilePath = "xslt/Max-testing.xslt";
		//System.out.println("Processing XML using " + xslFilePath);
		
		
		
		File xmlFile = new File(xmlFilePath);
		File xslFile = new File(xslFilePath);
		
		Source xmlSource = new StreamSource(xmlFile);
		Source xslSource = new StreamSource(xslFile);
		Result result = new StreamResult(System.out);
		TransformerErrorListener tel = new TransformerErrorListener();
		//TransformerFactory tf = TransformerFactory.newInstance();
		TransformerFactory tf = new net.sf.saxon.TransformerFactoryImpl();
		System.out.println("Using transform factory:" + tf.getClass());
		Transformer transformer = tf.newTransformer(xslSource);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setErrorListener(tel);
		try{
			transformer.transform(xmlSource, result);
		}catch(TransformerException ex){
			ex.printStackTrace();
		}
	}
}
