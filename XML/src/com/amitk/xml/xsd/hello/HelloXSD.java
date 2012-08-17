package com.amitk.xml.xsd.hello;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class HelloXSD {

	String purchaseOrderXMLPath = "com/amitk/xml/xsd/hello";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			System.setProperty(	"javax.xml.parsers.DocumentBuilderfactory", 
								"org.apache.xerces.jaxp.DocumentBuilderFactoryImpl");
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setNamespaceAware(true);
			factory.setValidating(true);
			factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage",
								 "http://www.w3c.org/2001/XMLSchema");
			factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", 
								 "PurchaseOrderSchema.xsd");
			DocumentBuilder builder = factory.newDocumentBuilder();
			Validator handler = new Validator();
			builder.setErrorHandler(handler);
			builder.parse("PurchaseOrder.xml");
			
            if(handler.validationError==true)     
                System.out.println("XML Document has Error:"+handler.validationError+" "+handler.saxParseException.getMessage());       
            else  
                System.out.println("XML Document is valid");     

		}catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
