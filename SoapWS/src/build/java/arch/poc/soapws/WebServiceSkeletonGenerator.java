package arch.poc.soapws;

//import org.apache.axis2.wsdl.WSDL2Code;
import org.apache.cxf.tools.wsdlto.WSDLToJava;
import org.junit.Test;

public class WebServiceSkeletonGenerator {

/*
    @Test
    public void generateAxisWsStub() throws Exception {
        WSDL2Code.main(new String[]{
                "-ss",
                "-sd",
                "-S", "src/main/java",
                "-R", "src/main/webapp/META-INF",
                "-ns2p", "http://www.matson.com=com.matson.vindecoding.webservice",
                "-uri", "src/main/resources/MatsonVinDecodingService.wsdl"});
        System.out.println("Done axis!");
    }
*/

    @Test
    public void generateCXFWsStub() throws Exception {
        WSDLToJava.main(new String[]{
                "-server",
                "-d", "src/main/java",
                "-p", "http://www.matson.com=com.matson.vindecoding.webservice",
                "src/main/resources/MatsonVinDecodingService.wsdl"});
        System.out.println("Done CXF!");
    }

    @Test
    public void generateCXFWsClient() throws Exception {
        WSDLToJava.main(new String[]{
                "-client",
                "-d", "src/test/java",
                "-p", "http://www.matson.com=com.matson.vindecoding.webservice.client",
                "src/main/resources/MatsonVinDecodingService.wsdl"});
        System.out.println("Done CXF!");
    }

}