package si.extension;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.xml.DefaultXmlPayloadConverter;
import org.springframework.integration.xml.XmlPayloadConverter;
import org.springframework.integration.xml.splitter.XPathMessageSplitter;
import org.springframework.util.Assert;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.xpath.XPathExpression;
import org.springframework.xml.xpath.XPathExpressionFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 8/29/11
 * Time: 6:24 PM
 * To change this template use File | Settings | File Templates.
 */
public class XPathSplitterWithCustomTailMessageAppender{

    private final XPathExpression xpathExpression;

    private volatile boolean createDocuments;

    private volatile DocumentBuilderFactory documentBuilderFactory;

    private volatile XmlPayloadConverter xmlPayloadConverter = new DefaultXmlPayloadConverter();


    public XPathSplitterWithCustomTailMessageAppender(String expression) {
        this(expression, new HashMap<String, String>());
    }

    public XPathSplitterWithCustomTailMessageAppender(String expression, Map<String, String> namespaces) {
        this(XPathExpressionFactory.createXPathExpression(expression, namespaces));
    }

    public XPathSplitterWithCustomTailMessageAppender(XPathExpression xpathExpression) {
        this.xpathExpression = xpathExpression;
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        this.documentBuilderFactory.setNamespaceAware(true);
    }


    public void setCreateDocuments(boolean createDocuments) {
        this.createDocuments = createDocuments;
    }


    public void setDocumentBuilder(DocumentBuilderFactory documentBuilderFactory) {
        Assert.notNull(documentBuilderFactory, "DocumentBuilderFactory must not be null");
        this.documentBuilderFactory = documentBuilderFactory;
    }

    public void setXmlPayloadConverter(XmlPayloadConverter xmlPayloadConverter) {
        Assert.notNull(xmlPayloadConverter, "XmlPayloadConverter must not be null");
        this.xmlPayloadConverter = xmlPayloadConverter;
    }



    protected List<?> splitMessageInternal(Message<?> message) {
        try {
            Object payload = message.getPayload();
            List<?> result = null;
            if (payload instanceof Node) {
                //Returns List<Node>
                result = splitNode((Node) payload);
            }
            else {
                Document document = this.xmlPayloadConverter.convertToDocument(payload);
                Assert.notNull(document, "unsupported payload type [" + payload.getClass().getName() + "]");
                //Return List<String>
                result = splitDocument(document);
            }
            return result;
        }
        catch (ParserConfigurationException e) {
            throw new MessagingException(message, "failed to create DocumentBuilder", e);
        }
        catch (Exception e) {
            throw new MessagingException(message, "failed to split Message payload", e);
        }
    }

    private List<String> splitDocument(Document document) throws Exception {
        List<Node> nodes = splitNode(document);
        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        List<String> splitStrings = new ArrayList<String>(nodes.size());
        for (Node nodeFromList : nodes) {
            StringResult result = new StringResult();
            transformer.transform(new DOMSource(nodeFromList), result);
            splitStrings.add(result.toString());
        }
        return splitStrings;
    }

    @SuppressWarnings("unchecked")
    private List<Node> splitNode(Node node) throws ParserConfigurationException {
        List<Node> nodeList = this.xpathExpression.evaluateAsNodeList(node);
        if (nodeList.size() == 0) {
            throw new IllegalArgumentException("failed to split message with XPath expression: " + this.xpathExpression);
        }
        if (this.createDocuments) {
            return convertNodesToDocuments(nodeList);
        }
        return nodeList;
    }

    private List<Node> convertNodesToDocuments(List<Node> nodes) throws ParserConfigurationException {
        DocumentBuilder documentBuilder = this.getNewDocumentBuilder();
        List<Node> documents = new ArrayList<Node>(nodes.size());
        for (Node node : nodes) {
            Document document = documentBuilder.newDocument();
            document.appendChild(document.importNode(node, true));
            documents.add(document);
        }
        return documents;
    }

    private DocumentBuilder getNewDocumentBuilder() throws ParserConfigurationException {
        synchronized (this.documentBuilderFactory) {
            return this.documentBuilderFactory.newDocumentBuilder();
        }
    }


    public Object splitAndAppendTailMessage(Message<?> message) {
        List list = splitMessageInternal(message);
        list.add("TAIL MESSAGE");
        return list;
    }
}
