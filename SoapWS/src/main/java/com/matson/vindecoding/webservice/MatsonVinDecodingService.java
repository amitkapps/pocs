package com.matson.vindecoding.webservice;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.5.6
 * 2012-10-25T01:52:46.644-07:00
 * Generated source version: 2.5.6
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