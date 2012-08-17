
package com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.matson.cas.service.cache.client.cfg.xml.jaxb.bindings
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link LruEvictionType }
     * 
     */
    public LruEvictionType createLruEvictionType() {
        return new LruEvictionType();
    }

    /**
     * Create an instance of {@link EvictionConfigType }
     * 
     */
    public EvictionConfigType createEvictionConfigType() {
        return new EvictionConfigType();
    }

    /**
     * Create an instance of {@link CasCache }
     * 
     */
    public CasCache createCasCache() {
        return new CasCache();
    }

    /**
     * Create an instance of {@link LocalType }
     * 
     */
    public LocalType createLocalType() {
        return new LocalType();
    }

    /**
     * Create an instance of {@link CacheModeType }
     * 
     */
    public CacheModeType createCacheModeType() {
        return new CacheModeType();
    }

    /**
     * Create an instance of {@link ClusterType }
     * 
     */
    public ClusterType createClusterType() {
        return new ClusterType();
    }

    /**
     * Create an instance of {@link RegionType }
     * 
     */
    public RegionType createRegionType() {
        return new RegionType();
    }

}
