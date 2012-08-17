package Jaxp;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: May 8, 2010
 * Time: 11:43:06 PM
 * To change this template use File | Settings | File Templates.
 */
public class XPathEvaluation {
    public static void main(String args[]) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("C:\\AmitK\\work\\project\\poc\\Projects\\Jaxp\\src\\test\\resources\\test.xml.xsd"));
        builderFactory.setSchema(schema);

        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new File("C:\\AmitK\\work\\project\\poc\\Projects\\Jaxp\\src\\test\\resources\\test.xml"));
        //DOMSource domSource = new DOMSource(document);
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        xPath.setNamespaceContext(new CasCacheNamespaceContext());
        XPathExpression expression = xPath.compile("pre:cas-cache/pre:default-eviction-config/pre:lru-eviction/@time-to-idle-secs");
        Attr a = (Attr)expression.evaluate(document, XPathConstants.NODE);
        System.out.println("App Root Node: " + a.getValue());

    }
}
