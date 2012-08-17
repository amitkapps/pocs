package com.amitk.xslt.hello;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.ErrorListener;
import javax.xml.transform.TransformerException;

/**
 * A simple Error Listener to be used by the XML Transformer.
 * @author axk
 *
 */
public class TransformerErrorListener implements ErrorListener {
	public TransformerException transformerException = null;
	ArrayList errors = new ArrayList();
	ArrayList warnings = new ArrayList();
	
	ErrorListener el;
	public TransformerErrorListener(ErrorListener el){
		this.el = el;
	}
	
	public TransformerErrorListener() {
		// TODO Auto-generated constructor stub
	}
	
	public void error(TransformerException e) {
		//just keep the first exception;
		if(null == transformerException)
			this.transformerException = e;
		this.errors.add(getTransformerExceptionDetails(e));
	}
	
	public void fatalError(TransformerException te) {
		//override with the fatal exception
		this.transformerException = te;
		this.errors.add(getTransformerExceptionDetails(te));
	}
	
	public void warning(TransformerException te) {
		this.warnings.add(getTransformerExceptionDetails(te));
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
	private String getTransformerExceptionDetails(TransformerException te){
		return new StringBuffer("Error: ")
		.append(te.getMessageAndLocation())
		.toString();
	}
	
	public TransformerException getTransformerException(){
		return transformerException;
	}
}
