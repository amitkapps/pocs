package com.amitk.xml.xsd.hello;

import java.io.File;

import org.apache.xerces.parsers.DOMParser;


public class HelloXSDSchemaValidationDOM {

	String purchaseOrderXMLPath = "com/amitk/xml/xsd/hello";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			//File docFile = new File("src/com/amitk/xml/xsd/hello/PurchaseOrder.xml");
			String schemaFileName = 
				//"PurchaseOrderSchema.xsd";
				"CasCache.xsd";
			String xmlFileName = 
				//"PurchaseOrder.xml";
				"cas_cache_clustered.region.config.xml";
			
			DOMParser parser = new DOMParser();
			parser.setFeature("http://xml.org/sax/features/validation", true);
			parser.setFeature("http://apache.org/xml/features/validation/schema", true);
			parser.setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",
			//parser.setProperty("http://apache.org/xml/properties/schema/external-schemaLocation",
							   "C:\\AmitK\\work\\project\\Learning\\EclipseWorkSpace\\XML\\src\\com\\amitk\\xml\\xsd\\hello\\" + schemaFileName);
			Validator errorHander = new Validator();
			parser.setErrorHandler(errorHander);
			parser.parse("src/com/amitk/xml/xsd/hello/" + xmlFileName);
			if (errorHander.hasErrors()) {
				errorHander.saxParseException.printStackTrace();
			} else {
				System.out.println("xml file parsed successfully");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}		
	}

}
