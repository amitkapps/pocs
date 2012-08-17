
package com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * 
 *                 A named region under which all the application's cached objects will be bound.
 *             
 * 
 * <p>Java class for region-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="region-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="eviction-config" type="{http://www.matson.com/schema/cas/cache}eviction-config-type"/>
 *       &lt;/sequence>
 *       &lt;attribute name="node-name" use="required" type="{http://www.w3.org/2001/XMLSchema}ID" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "region-type", namespace = "http://www.matson.com/schema/cas/cache", propOrder = {
    "evictionConfig"
})
public class RegionType {

    @XmlElement(name = "eviction-config", namespace = "http://www.matson.com/schema/cas/cache", required = true)
    protected EvictionConfigType evictionConfig;
    @XmlAttribute(name = "node-name", required = true)
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlID
    @XmlSchemaType(name = "ID")
    protected String nodeName;

    /**
     * Gets the value of the evictionConfig property.
     * 
     * @return
     *     possible object is
     *     {@link EvictionConfigType }
     *     
     */
    public EvictionConfigType getEvictionConfig() {
        return evictionConfig;
    }

    /**
     * Sets the value of the evictionConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link EvictionConfigType }
     *     
     */
    public void setEvictionConfig(EvictionConfigType value) {
        this.evictionConfig = value;
    }

    /**
     * Gets the value of the nodeName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeName() {
        return nodeName;
    }

    /**
     * Sets the value of the nodeName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeName(String value) {
        this.nodeName = value;
    }

}
