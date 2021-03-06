package com.matson.vindecoding.webservice;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.5.6
 * 2012-10-25T01:52:46.703-07:00
 * Generated source version: 2.5.6
 * 
 */
@WebServiceClient(name = "MatsonVinDecodingService", 
                  wsdlLocation = "src/main/resources/MatsonVinDecodingService.wsdl",
                  targetNamespace = "http://www.matson.com") 
public class MatsonVinDecodingService_Service extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://www.matson.com", "MatsonVinDecodingService");
    public final static QName MatsonVinDecodingService = new QName("http://www.matson.com", "MatsonVinDecodingService");
    static {
        URL url = MatsonVinDecodingService_Service.class.getResource("src/main/resources/MatsonVinDecodingService.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(MatsonVinDecodingService_Service.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "src/main/resources/MatsonVinDecodingService.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public MatsonVinDecodingService_Service(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public MatsonVinDecodingService_Service(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public MatsonVinDecodingService_Service() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public MatsonVinDecodingService_Service(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public MatsonVinDecodingService_Service(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public MatsonVinDecodingService_Service(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns MatsonVinDecodingService
     */
    @WebEndpoint(name = "MatsonVinDecodingService")
    public MatsonVinDecodingService getMatsonVinDecodingService() {
        return super.getPort(MatsonVinDecodingService, MatsonVinDecodingService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns MatsonVinDecodingService
     */
    @WebEndpoint(name = "MatsonVinDecodingService")
    public MatsonVinDecodingService getMatsonVinDecodingService(WebServiceFeature... features) {
        return super.getPort(MatsonVinDecodingService, MatsonVinDecodingService.class, features);
    }

}
