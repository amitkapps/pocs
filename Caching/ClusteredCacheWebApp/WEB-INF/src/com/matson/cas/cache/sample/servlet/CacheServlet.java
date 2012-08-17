package com.matson.cas.cache.sample.servlet;

import javax.naming.Context;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.jboss.cache.TreeCache;
import weblogic.jndi.Environment;


/**
 * A superclass that looks up the cache in JNDI and maintains a static reference 
 * for subclasses, thereby saving on repeated JNDI lookups.
 * 
 * @author Manik Surtani (manik@surtani.org)
 */
public abstract class CacheServlet extends HttpServlet
{
    
    private static final String JNDI_NAME = "CASSharedClusterCacheManager";
    protected static final String ROOT_NODE = "/test-root-node";
    private static Logger logger = Logger.getLogger(CacheServlet.class);
    private static TreeCache _cache;
    
    protected TreeCache getCache()
    {
        logger.debug("TreeCache reference requested.");
        if (_cache == null)
        {
            logger.debug("No reference available, first time used?");
            lookupCacheInJNDI();
        }
        
        return _cache;
    }
    
    private void lookupCacheInJNDI()
    {
        try
        {
            logger.debug("Performing JNDI lookup");

            // Using WL specifics again.
            Environment env = new Environment();
            Context ctx = env.getInitialContext();
	        _cache = (TreeCache) ctx.lookup(JNDI_NAME);
        }
        catch (Exception e)
        {
            logger.error("Problems looking up cache in JNDI", e);
        }
    }
    
}
