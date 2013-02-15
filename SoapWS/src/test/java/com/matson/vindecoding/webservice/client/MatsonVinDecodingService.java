package com.matson.vindecoding.webservice.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.2.6
 * Wed Oct 24 09:26:25 MST 2012
 * Generated source version: 2.2.6
 * 
 */
 
@WebService(targetNamespace = "http://www.matson.com", name = "MatsonVinDecodingService")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface MatsonVinDecodingService {

    @WebResult(name = "vehicleResults", targetNamespace = "http://www.matson.com", partName = "vehicleResults")
    @WebMethod
    public VehicleInfo getVehicleDetails(
        @WebParam(partName = "vinNumber", name = "vinNumber")
        java.lang.String vinNumber
    );
}