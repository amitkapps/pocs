
package com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for cache-mode-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="cache-mode-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="cluster" type="{http://www.matson.com/schema/cas/cache}cluster-type"/>
 *         &lt;element name="local" type="{http://www.matson.com/schema/cas/cache}local-type"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "cache-mode-type", namespace = "http://www.matson.com/schema/cas/cache", propOrder = {
    "cluster",
    "local"
})
public class CacheModeType {

    @XmlElement(namespace = "http://www.matson.com/schema/cas/cache")
    protected ClusterType cluster;
    @XmlElement(namespace = "http://www.matson.com/schema/cas/cache")
    protected LocalType local;

    /**
     * Gets the value of the cluster property.
     * 
     * @return
     *     possible object is
     *     {@link ClusterType }
     *     
     */
    public ClusterType getCluster() {
        return cluster;
    }

    /**
     * Sets the value of the cluster property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClusterType }
     *     
     */
    public void setCluster(ClusterType value) {
        this.cluster = value;
    }

    /**
     * Gets the value of the local property.
     * 
     * @return
     *     possible object is
     *     {@link LocalType }
     *     
     */
    public LocalType getLocal() {
        return local;
    }

    /**
     * Sets the value of the local property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalType }
     *     
     */
    public void setLocal(LocalType value) {
        this.local = value;
    }

}
