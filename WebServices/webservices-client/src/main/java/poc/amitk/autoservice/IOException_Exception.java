
package poc.amitk.autoservice;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.2.3
 * Fri Feb 15 03:33:44 MST 2013
 * Generated source version: 2.2.3
 * 
 */

@WebFault(name = "IOException", targetNamespace = "http://amitk.poc/AutoService")
public class IOException_Exception extends Exception {
    public static final long serialVersionUID = 20130215033344L;
    
    private poc.amitk.autoservice.IOException ioException;

    public IOException_Exception() {
        super();
    }
    
    public IOException_Exception(String message) {
        super(message);
    }
    
    public IOException_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public IOException_Exception(String message, poc.amitk.autoservice.IOException ioException) {
        super(message);
        this.ioException = ioException;
    }

    public IOException_Exception(String message, poc.amitk.autoservice.IOException ioException, Throwable cause) {
        super(message, cause);
        this.ioException = ioException;
    }

    public poc.amitk.autoservice.IOException getFaultInfo() {
        return this.ioException;
    }
}
