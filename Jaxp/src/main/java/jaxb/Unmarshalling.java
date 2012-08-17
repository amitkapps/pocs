package jaxb;

import com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings.CasCache;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: May 9, 2010
 * Time: 1:18:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class Unmarshalling {

    public static void main(String args[]) throws JAXBException, SAXException {
        JAXBContext context = JAXBContext.newInstance("com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings");
        Unmarshaller unmarshaller = context.createUnmarshaller();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File("C:\\AmitK\\work\\project\\poc\\Projects\\Jaxp\\src\\test\\resources\\test.xml.xsd"));
        unmarshaller.setSchema(schema);
        
        CasCache cache = (CasCache) unmarshaller.unmarshal(new File("C:\\AmitK\\work\\project\\poc\\Projects\\Jaxp\\src\\test\\resources\\test.xml"));
        System.out.println("App Root Node:" + cache.getApplicationRootNode());

        System.out.println("Default Eviction- LRU: " + cache.getDefaultEvictionConfig().getLruEviction());
        System.out.println("Cache Mode:" + cache.getCacheMode().getCluster().getCacheJndiName());

    }
}
