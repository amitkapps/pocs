
package com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for eviction-config-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="eviction-config-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="lru-eviction" type="{http://www.matson.com/schema/cas/cache}lru-eviction-type"/>
 *         &lt;element name="no-eviction" type="{http://www.w3.org/2001/XMLSchema}anyType"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "eviction-config-type", namespace = "http://www.matson.com/schema/cas/cache", propOrder = {
    "lruEviction",
    "noEviction"
})
public class EvictionConfigType {

    @XmlElement(name = "lru-eviction", namespace = "http://www.matson.com/schema/cas/cache")
    protected LruEvictionType lruEviction;
    @XmlElement(name = "no-eviction", namespace = "http://www.matson.com/schema/cas/cache")
    protected Object noEviction;

    /**
     * Gets the value of the lruEviction property.
     * 
     * @return
     *     possible object is
     *     {@link LruEvictionType }
     *     
     */
    public LruEvictionType getLruEviction() {
        return lruEviction;
    }

    /**
     * Sets the value of the lruEviction property.
     * 
     * @param value
     *     allowed object is
     *     {@link LruEvictionType }
     *     
     */
    public void setLruEviction(LruEvictionType value) {
        this.lruEviction = value;
    }

    /**
     * Gets the value of the noEviction property.
     * 
     * @return
     *     possible object is
     *     {@link Object }
     *     
     */
    public Object getNoEviction() {
        return noEviction;
    }

    /**
     * Sets the value of the noEviction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Object }
     *     
     */
    public void setNoEviction(Object value) {
        this.noEviction = value;
    }

}
