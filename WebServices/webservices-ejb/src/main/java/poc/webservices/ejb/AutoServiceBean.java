package poc.webservices.ejb;

import org.apache.commons.io.IOUtils;
import poc.webservices.Unit;

import javax.activation.DataHandler;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.mail.util.ByteArrayDataSource;
import javax.xml.bind.annotation.XmlMimeType;
import javax.xml.ws.BindingType;
import javax.xml.ws.soap.SOAPBinding;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 2/14/13
 * Time: 11:05 PM
 * To change this template use File | Settings | File Templates.
 */
@Stateless(name = "AutoServiceEJB", mappedName = "AutoServiceEJB")
@WebService(name = "AutoServiceEndPoint", serviceName = "AutoService", portName = "AutoServicePort",
        targetNamespace = "http://amitk.poc/AutoService")
// Below annotation activates MTOM, without this the PDF response
// would be inlined as base64Binary within the SOAP response
@BindingType(value= SOAPBinding.SOAP12HTTP_MTOM_BINDING)
public class AutoServiceBean {
    public AutoServiceBean() {
    }

    @WebResult(name = "unit")
    public List<Unit> getStatuses(){
        ArrayList<Unit> units = new ArrayList<Unit>();

        units.add(new Unit().setUnitId(1).setBookingNumber("B1"));
        units.add(new Unit().setUnitId(2).setBookingNumber("B2"));

        return units;
    }

    @WebResult(name = "pdf")
    @XmlMimeType("application/octet-stream")
    public DataHandler getDeliveryReceiptPDF(@WebParam(name = "filePath") String path) throws IOException {

        byte[] bytes = IOUtils.toByteArray(new FileInputStream(path));
        System.out.println("Byte array size: " + bytes.length);
        ByteArrayDataSource ds = new ByteArrayDataSource(bytes, "application/pdf");
        System.out.println("Returning DataHandler");
        return new DataHandler(ds);
    }
}
