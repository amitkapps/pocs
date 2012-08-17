
package com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Existence of this tag specifies that any changes to cache are synchronized to other members of the cache
 *                 in the cluster. In a clustered cache environment, any updates to the cache get synchronized with other
 *                 caches (residing in different jvms) that are members of the cache cluster. If this tag is not specified
 *                 the cache operates in local mode with an independent cache instance.
 *             
 * 
 * <p>Java class for cluster-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cluster-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="cache-jndi-name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cluster-type", namespace = "http://www.matson.com/schema/cas/cache")
public class ClusterType {

    @XmlAttribute(name = "cache-jndi-name")
    protected String cacheJndiName;

    /**
     * Gets the value of the cacheJndiName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCacheJndiName() {
        return cacheJndiName;
    }

    /**
     * Sets the value of the cacheJndiName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCacheJndiName(String value) {
        this.cacheJndiName = value;
    }

}
