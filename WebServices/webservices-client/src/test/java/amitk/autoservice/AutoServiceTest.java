package amitk.autoservice;

import org.junit.Test;
import poc.amitk.autoservice.AutoService;
import poc.amitk.autoservice.AutoServiceEndPoint;
import poc.amitk.autoservice.AutoServiceWS;
import poc.amitk.autoservice.IOException_Exception;

import javax.activation.DataHandler;
import javax.xml.ws.Binding;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.soap.SOAPBinding;
import java.io.*;
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
        AutoServiceEndPoint autoService = new AutoService().getAutoServicePort();
//        Binding binding = ((BindingProvider)autoService).getBinding();
//        ((SOAPBinding)binding).setMTOMEnabled(true);
        DataHandler deliveryReceiptPDF = autoService.getDeliveryReceiptPDF(1);

        //fos.write(deliveryReceiptPDF.getDataSource().getInputStream());

        try {
            // read this file into InputStream
            BufferedInputStream bis = new BufferedInputStream(deliveryReceiptPDF.getInputStream());

            // write the inputStream to a FileOutputStream
            FileOutputStream out = new FileOutputStream("/Users/amitkapps/Documents/test.pdf");

            int charsRead = 0;
            byte[] buffer = new byte[1024];

            while ( (charsRead = bis.read(buffer, 0, 1024)) != -1) {
                out.write(buffer, 0, charsRead);
            }

            bis.close();
            out.flush();
            out.close();

            System.out.println("New file created!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
