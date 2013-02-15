package amitk.autoservice;

import org.junit.Test;
import poc.amitk.autoservice.AutoService;
import poc.amitk.autoservice.AutoServiceWS;
import poc.amitk.autoservice.IOException_Exception;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: amitkapps
 * Date: 2/15/13
 * Time: 1:15 AM
 * To change this template use File | Settings | File Templates.
 */
public class AutoServiceTest {

    @Test
    public void testGetPDF() throws IOException_Exception, IOException {
        URL wsdlLocation = new URL("http://127.0.0.1:8180/webservices-ear-1.0-SNAPSHOT-webservices-ejb-1.0-SNAPSHOT/AutoServiceEJB?wsdl");
        AutoServiceWS autoService = new AutoService().getAutoServiceWSPort();
        byte[] deliveryReceiptPDF = autoService.getDeliveryReceiptPDF(1);
        FileOutputStream fos = new FileOutputStream("/Users/amitkapps/Documents/test.pdf");
        fos.write(deliveryReceiptPDF);
        fos.flush();
        fos.close();
    }
}
