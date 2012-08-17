
package com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Existence of this tag specifies the cache to operate in local mode with an independent cache instance.
 *             
 * 
 * <p>Java class for local-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="local-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="underlying-cache-config-path" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "local-type", namespace = "http://www.matson.com/schema/cas/cache")
public class LocalType {

    @XmlAttribute(name = "underlying-cache-config-path")
    protected String underlyingCacheConfigPath;

    /**
     * Gets the value of the underlyingCacheConfigPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnderlyingCacheConfigPath() {
        return underlyingCacheConfigPath;
    }

    /**
     * Sets the value of the underlyingCacheConfigPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnderlyingCacheConfigPath(String value) {
        this.underlyingCacheConfigPath = value;
    }

}
