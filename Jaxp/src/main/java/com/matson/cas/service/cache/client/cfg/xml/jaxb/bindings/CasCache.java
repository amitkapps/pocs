
package com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cache-mode" type="{http://www.matson.com/schema/cas/cache}cache-mode-type"/>
 *         &lt;element name="default-eviction-config" type="{http://www.matson.com/schema/cas/cache}eviction-config-type" minOccurs="0"/>
 *         &lt;element name="region" type="{http://www.matson.com/schema/cas/cache}region-type" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="application-root-node" use="required">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="[a-zA-Z_-]+"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cacheMode",
    "defaultEvictionConfig",
    "region"
})
@XmlRootElement(name = "cas-cache", namespace = "http://www.matson.com/schema/cas/cache")
public class CasCache {

    @XmlElement(name = "cache-mode", namespace = "http://www.matson.com/schema/cas/cache", required = true)
    protected CacheModeType cacheMode;
    @XmlElement(name = "default-eviction-config", namespace = "http://www.matson.com/schema/cas/cache")
    protected EvictionConfigType defaultEvictionConfig;
    @XmlElement(namespace = "http://www.matson.com/schema/cas/cache")
    protected List<RegionType> region;
    @XmlAttribute(name = "application-root-node", required = true)
    protected String applicationRootNode;

    /**
     * Gets the value of the cacheMode property.
     * 
     * @return
     *     possible object is
     *     {@link CacheModeType }
     *     
     */
    public CacheModeType getCacheMode() {
        return cacheMode;
    }

    /**
     * Sets the value of the cacheMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link CacheModeType }
     *     
     */
    public void setCacheMode(CacheModeType value) {
        this.cacheMode = value;
    }

    /**
     * Gets the value of the defaultEvictionConfig property.
     * 
     * @return
     *     possible object is
     *     {@link EvictionConfigType }
     *     
     */
    public EvictionConfigType getDefaultEvictionConfig() {
        return defaultEvictionConfig;
    }

    /**
     * Sets the value of the defaultEvictionConfig property.
     * 
     * @param value
     *     allowed object is
     *     {@link EvictionConfigType }
     *     
     */
    public void setDefaultEvictionConfig(EvictionConfigType value) {
        this.defaultEvictionConfig = value;
    }

    /**
     * Gets the value of the region property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the region property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRegion().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RegionType }
     * 
     * 
     */
    public List<RegionType> getRegion() {
        if (region == null) {
            region = new ArrayList<RegionType>();
        }
        return this.region;
    }

    /**
     * Gets the value of the applicationRootNode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getApplicationRootNode() {
        return applicationRootNode;
    }

    /**
     * Sets the value of the applicationRootNode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setApplicationRootNode(String value) {
        this.applicationRootNode = value;
    }

}
