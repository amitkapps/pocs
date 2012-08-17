package com.amitk.xml.xsd.hello;

import java.io.File;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.crimson.tree.TextNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DomManipulation {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String xmlFilePath = "./xml/Employee.xml";
			Document doc = createDom(xmlFilePath);
			doc = manipulateDom(doc);
			printDocument(doc);
			
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
	}

	public static Document createDom(String xmlFilePath)
	throws Throwable{
		Document doc = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		doc = db.parse(new File(xmlFilePath));
		return doc;
	}
	
	public static Document manipulateDom(Document doc)
	throws Throwable{
		DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		
		Node node = doc.getElementsByTagName("name").item(0);
		System.out.println(node.getChildNodes().item(0).getNodeValue());
		node.getChildNodes().item(0).setNodeValue("Amit.K.");
		Element elem = doc.createElement("designation");
		elem.appendChild(doc.createTextNode("s/w Engg"));
		doc.getDocumentElement().appendChild(elem);
		Document newDoc = (Document)doc.cloneNode(true);
		Node oldParent = newDoc.getDocumentElement().cloneNode(true);
		newDoc.removeChild(newDoc.getDocumentElement());
		Node employeesElem =  newDoc.createElement("employees");
		newDoc.appendChild(employeesElem).appendChild(oldParent);
		Node newEmp = oldParent.cloneNode(true);
		getFirstNodeByName(newEmp, "name").getChildNodes().item(0).setNodeValue("sam");
		employeesElem.appendChild(newEmp);
		
		return newDoc;
	}
	
	public static void printDocument(Document doc)
	throws Throwable{
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.transform(new DOMSource(doc), new StreamResult(System.out));

	}
	
	public static Node getFirstNodeByName(Node node, String name){
		NodeList nodes = node.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node tempNode = nodes.item(i);
			if(tempNode.getNodeName().equals(name))
				return tempNode;
		}
		return null;
}
}
