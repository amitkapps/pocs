
package poc.amitk.autoservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the poc.amitk.autoservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetStatuses_QNAME = new QName("http://amitk.poc/AutoService", "getStatuses");
    private final static QName _IOException_QNAME = new QName("http://amitk.poc/AutoService", "IOException");
    private final static QName _GetStatusesResponse_QNAME = new QName("http://amitk.poc/AutoService", "getStatusesResponse");
    private final static QName _GetDeliveryReceiptPDFResponse_QNAME = new QName("http://amitk.poc/AutoService", "getDeliveryReceiptPDFResponse");
    private final static QName _GetDeliveryReceiptPDF_QNAME = new QName("http://amitk.poc/AutoService", "getDeliveryReceiptPDF");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: poc.amitk.autoservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetStatusesResponse }
     * 
     */
    public GetStatusesResponse createGetStatusesResponse() {
        return new GetStatusesResponse();
    }

    /**
     * Create an instance of {@link GetStatuses }
     * 
     */
    public GetStatuses createGetStatuses() {
        return new GetStatuses();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link Unit }
     * 
     */
    public Unit createUnit() {
        return new Unit();
    }

    /**
     * Create an instance of {@link GetDeliveryReceiptPDF }
     * 
     */
    public GetDeliveryReceiptPDF createGetDeliveryReceiptPDF() {
        return new GetDeliveryReceiptPDF();
    }

    /**
     * Create an instance of {@link GetDeliveryReceiptPDFResponse }
     * 
     */
    public GetDeliveryReceiptPDFResponse createGetDeliveryReceiptPDFResponse() {
        return new GetDeliveryReceiptPDFResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStatuses }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amitk.poc/AutoService", name = "getStatuses")
    public JAXBElement<GetStatuses> createGetStatuses(GetStatuses value) {
        return new JAXBElement<GetStatuses>(_GetStatuses_QNAME, GetStatuses.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amitk.poc/AutoService", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetStatusesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amitk.poc/AutoService", name = "getStatusesResponse")
    public JAXBElement<GetStatusesResponse> createGetStatusesResponse(GetStatusesResponse value) {
        return new JAXBElement<GetStatusesResponse>(_GetStatusesResponse_QNAME, GetStatusesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeliveryReceiptPDFResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amitk.poc/AutoService", name = "getDeliveryReceiptPDFResponse")
    public JAXBElement<GetDeliveryReceiptPDFResponse> createGetDeliveryReceiptPDFResponse(GetDeliveryReceiptPDFResponse value) {
        return new JAXBElement<GetDeliveryReceiptPDFResponse>(_GetDeliveryReceiptPDFResponse_QNAME, GetDeliveryReceiptPDFResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDeliveryReceiptPDF }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://amitk.poc/AutoService", name = "getDeliveryReceiptPDF")
    public JAXBElement<GetDeliveryReceiptPDF> createGetDeliveryReceiptPDF(GetDeliveryReceiptPDF value) {
        return new JAXBElement<GetDeliveryReceiptPDF>(_GetDeliveryReceiptPDF_QNAME, GetDeliveryReceiptPDF.class, null, value);
    }

}
