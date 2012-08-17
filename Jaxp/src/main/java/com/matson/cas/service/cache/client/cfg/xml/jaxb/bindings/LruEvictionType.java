
package com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * 
 *                 Any cached items bound under the associated region's node will be evicted as per the Least Recently
 *                 Used algorithm.
 *             
 * 
 * <p>Java class for lru-eviction-type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="lru-eviction-type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="min-time-to-live-secs" type="{http://www.w3.org/2001/XMLSchema}int" default="-1" />
 *       &lt;attribute name="time-to-idle-secs" type="{http://www.w3.org/2001/XMLSchema}int" default="-1" />
 *       &lt;attribute name="max-time-to-live-secs" type="{http://www.w3.org/2001/XMLSchema}int" default="-1" />
 *       &lt;attribute name="max-node-count" type="{http://www.w3.org/2001/XMLSchema}int" default="0" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "lru-eviction-type", namespace = "http://www.matson.com/schema/cas/cache")
public class LruEvictionType {

    @XmlAttribute(name = "min-time-to-live-secs")
    protected Integer minTimeToLiveSecs;
    @XmlAttribute(name = "time-to-idle-secs")
    protected Integer timeToIdleSecs;
    @XmlAttribute(name = "max-time-to-live-secs")
    protected Integer maxTimeToLiveSecs;
    @XmlAttribute(name = "max-node-count")
    protected Integer maxNodeCount;

    /**
     * Gets the value of the minTimeToLiveSecs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getMinTimeToLiveSecs() {
        if (minTimeToLiveSecs == null) {
            return -1;
        } else {
            return minTimeToLiveSecs;
        }
    }

    /**
     * Sets the value of the minTimeToLiveSecs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMinTimeToLiveSecs(Integer value) {
        this.minTimeToLiveSecs = value;
    }

    /**
     * Gets the value of the timeToIdleSecs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getTimeToIdleSecs() {
        if (timeToIdleSecs == null) {
            return -1;
        } else {
            return timeToIdleSecs;
        }
    }

    /**
     * Sets the value of the timeToIdleSecs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTimeToIdleSecs(Integer value) {
        this.timeToIdleSecs = value;
    }

    /**
     * Gets the value of the maxTimeToLiveSecs property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getMaxTimeToLiveSecs() {
        if (maxTimeToLiveSecs == null) {
            return -1;
        } else {
            return maxTimeToLiveSecs;
        }
    }

    /**
     * Sets the value of the maxTimeToLiveSecs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxTimeToLiveSecs(Integer value) {
        this.maxTimeToLiveSecs = value;
    }

    /**
     * Gets the value of the maxNodeCount property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public int getMaxNodeCount() {
        if (maxNodeCount == null) {
            return  0;
        } else {
            return maxNodeCount;
        }
    }

    /**
     * Sets the value of the maxNodeCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMaxNodeCount(Integer value) {
        this.maxNodeCount = value;
    }

}
