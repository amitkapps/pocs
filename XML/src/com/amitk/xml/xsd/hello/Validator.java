package com.amitk.xml.xsd.hello;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * A simple Error handler to be used by the XML Parser.
 * @author axk
 *
 */
public class Validator implements ErrorHandler {
	public SAXParseException saxParseException = null;
	ArrayList errors = new ArrayList();
	ArrayList warnings = new ArrayList();
	
	public void error(SAXParseException e) throws SAXException {
		//just keep the first exception;
		if(null == saxParseException)
			this.saxParseException = e;
		this.errors.add(getSAXExceptionDetails(e));
	}
	
	public void fatalError(SAXParseException e) throws SAXException {
		//override with the fatal exception
		this.saxParseException = e;
		this.errors.add(getSAXExceptionDetails(e));
	}
	
	public void warning(SAXParseException e) throws SAXException {
		this.warnings.add(getSAXExceptionDetails(e));
	}
	
	/**
	 * @return list containing the error details
	 */
	public List getErrors(){
		return errors;
	}
	
	/**
	 * @return true if any errors were notified by the XML Parser
	 */
	public boolean hasErrors(){
		return (errors.size() > 0);
	}
	
	/**
	 * @return list containing the warning details.
	 */
	public List getWarnings(){
		return warnings;
	}
	
	/**
	 * 
	 * @return true if any warnings were notified by the XML Parser.
	 */
	public boolean hasWarnings(){
		return( warnings.size() > 0);
	}
	
	/**
	 * @param spe
	 * @return simple text string with details on the SAXException message
	 */
	private String getSAXExceptionDetails(SAXParseException spe){
		return new StringBuffer("Error at Line:")
		.append(spe.getLineNumber())
		.append(", Column:")
		.append(spe.getColumnNumber())
		.append("-")
		.append(spe.getMessage())
		.toString();
	}
	
	public SAXParseException getSAXParseException(){
		return saxParseException;
	}
}
