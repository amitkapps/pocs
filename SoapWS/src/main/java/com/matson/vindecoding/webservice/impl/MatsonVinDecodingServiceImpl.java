package com.matson.vindecoding.webservice.impl;

import com.matson.vindecoding.webservice.MatsonVinDecodingService;
import com.matson.vindecoding.webservice.VehicleInfo;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 10/23/12
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
@WebService(endpointInterface = "com.matson.vindecoding.webservice.MatsonVinDecodingService", targetNamespace = "http://www.matson.com",
        name = "MatsonVinDecodingService", serviceName = "MatsonVinDecodingService", portName = "MatsonVinDecodingService")
public class MatsonVinDecodingServiceImpl implements MatsonVinDecodingService {

    @WebResult(name = "vehicleResults", targetNamespace = "http://www.matson.com", partName = "vehicleResults")
    @WebMethod
    @Override
    public VehicleInfo getVehicleDetails(@WebParam(partName = "vinNumber", name = "vinNumber") String vinNumber) {
        System.out.println("Request to decode vin:" + vinNumber);
        VehicleInfo vehicleInfo = new VehicleInfo();
        vehicleInfo.setVinNumber(vinNumber);
        vehicleInfo.setMake("make");
        vehicleInfo.setModel("model");
        vehicleInfo.setYear(9999);
        vehicleInfo.setAssemblyPlant("assembly plant");
        vehicleInfo.setBodyType("body type");
        vehicleInfo.setVehicleType("vehicle type");

        return vehicleInfo;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
