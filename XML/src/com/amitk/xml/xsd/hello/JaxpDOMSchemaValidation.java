package com.amitk.xml.xsd.hello;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.xerces.parsers.DOMParser;


public class JaxpDOMSchemaValidation {

	String purchaseOrderXMLPath = "com/amitk/xml/xsd/hello";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Validator errorHander;
		try{
			//File docFile = new File("src/com/amitk/xml/xsd/hello/PurchaseOrder.xml");
			String schemaFileName = 
				//"PurchaseOrderSchema.xsd";
				"Order.xsd";
			String xmlFileName = 
				//"PurchaseOrder.xml";
				"OrderCreate.xml";
			String nameSpace = "http://www.matson.com/milportal/schema";
			

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setValidating(true);
			dbf.setNamespaceAware(true);
			dbf.setAttribute(	"http://java.sun.com/xml/jaxp/properties/schemaLanguage",
		    					"http://www.w3.org/2001/XMLSchema");
			dbf.setAttribute(	"http://java.sun.com/xml/jaxp/properties/schemaSource",
							//nameSpace + " " + 
						"http://10.8.4.118:9050/MILOrderTrackEEM/static/Order.xsd");
			
			/*
			parser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation",
								nameSpace + " " + 
							   //"C:\\AmitK\\work\\project\\Learning\\EclipseWorkSpace\\XML\\src\\com\\amitk\\xml\\xsd\\hello\\" + schemaFileName);
								"http://10.8.4.118:9050/MILOrderTrackEEM/static/Order.xsd");
			*/
			DocumentBuilder builder = dbf.newDocumentBuilder();
			System.out.println("Document builder impl:" + builder.getDOMImplementation().getClass().getName());

			errorHander = new Validator();
			builder.setErrorHandler(errorHander);
			//parser.setProperty(name, value)ErrorHandler(errorHander);
			//parser.parse("src/com/amitk/xml/xsd/hello/" + xmlFileName, errorHander);
			builder.parse("src/com/amitk/xml/xsd/hello/" + xmlFileName);

			if (errorHander.hasErrors()) {
				//errorHander.saxParseException.printStackTrace();
				System.out.println(errorHander.getErrors());
			} else {
				System.out.println("xml file parsed successfully");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
