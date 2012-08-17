package Jaxp;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

/**
 * Hello world!
 *
 */
public class XmlValidation 
{
    public static void main( String[] args ) throws SAXException, ParserConfigurationException, IOException {
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("C:\\AmitK\\work\\project\\poc\\Projects\\Jaxp\\src\\test\\resources\\test.xml.xsd"));
        System.out.println("Schema created: " + schema);
        Validator validator = schema.newValidator();

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        builderFactory.setSchema(schema);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new File("C:\\AmitK\\work\\project\\poc\\Projects\\Jaxp\\src\\test\\resources\\test.xml"));

        validator.validate(new DOMSource(document));
    }
}
