package com.matson.cas.cache;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.log4j.Logger;
import org.jboss.cache.PropertyConfigurator;
import org.jboss.cache.TreeCache;

import weblogic.jndi.WLContext;


/**
 * This startup/shutdown class kicks off a TreeCache and binds it in local JNDI.  Note that the JNDI name is
 * in the java: namespace, which means that it will not be replicated across a cluster of J2EE servers.
 * This class binds the cache to 'java:jbosscache' in JNDI.
 * <BR><BR>
 * Clients then access the cache by looking it up in JNDI.  JNDI is <I>not</I> used to replicate state
 * of the cache across a cluster - the TreeCache handles this by using JGroups that it sits on.  It is
 * a good idea for clients to hold a reference to the TreeCache the first time it is looked up in JNDI
 * (using a singleton helper class or a superclass, for example) to prevent unnecessary JNDI lookups.
 * <BR><BR>
 * See CacheServlet for an example of using a superclass.
 * <BR><BR>
 * Deploying this startup/shutdown class in your J2EE server, you need to have this class, along with the
 * following list of jars in your server's startup classpath.  You also need to pass in the 'start' argument 
 * to instruct this class to behave as a startup class, or 'stop' argument to instruct it to behave as a 
 * shutdown class.   
 * <BR><BR>
 * List of jars (from the jboss-cache-1.1 binary distribution) needed in server classpath:<BR>
 * <UL>
 * <LI>commons-logging.jar</LI>
 * <LI>concurrent.jar</LI>
 * <LI>javassist.jar</LI>
 * <LI>jboss-aop.jar</LI>
 * <LI>jboss-cache.jar</LI>
 * <LI>jboss-common.jar</LI>
 * <LI>jboss-jmx.jar</LI>
 * <LI>jboss-system.jar</LI>
 * <LI>je.jar</LI>
 * <LI>jgroups.jar</LI>
 * <LI>log4j.jar</LI>
 * <LI>trove.jar</LI>
 * </UL>
 * <BR><BR>
 * In addition, you will need a compiled version of this class in the classpath, as well as a path to a jboss-cache 
 * compliant XML configuration file.  This class looks for etc/jbosscache-config.xml in your classpath.  It is also a
 * good idea to have your log4j configuration file in the classpath!!
 * <BR><BR>
 */
public class SharedClusterCacheManager
{
    private static final String JNDI_NAME="CASSharedClusterCacheManager";
    private static final Logger logger = Logger.getLogger(SharedClusterCacheManager.class);  
    private static final String configFileName="conf/CASSharedClusterCache-service.xml";

    public static void main(String[] s) throws Exception
    {
        if (s.length == 0)
        {
            logger.fatal("This class needs to be called with 'start' or 'stop' as an argument!");
            throw new Exception("Insufficient args");
        }
        
        if (s[0].equals("start"))
        {
	        
	        logger.debug("Starting JBoss cache using config file " + configFileName);
	        
	        TreeCache _cache = new TreeCache();
	        
	        PropertyConfigurator pc = new PropertyConfigurator();
	        pc.configure(_cache, configFileName);
	        
	        _cache.createService();
	        _cache.startService();
	        
	        logger.debug("JBoss cache service running");
	        logger.debug("TreeCache object [" + _cache.hashCode() + "]");
	        _cache.put("/jboss-cache-manager", "Sample Cache Entry", "This is a sample");
	        setCacheInJNDI( _cache );
	        return;
        }
        
        if (s[0].equals("stop"))
        {
            logger.debug("Starting JBoss cache");
            TreeCache _cache = getCacheFromJNDI();
            _cache.stopService();
            _cache.destroyService();
            logger.debug("JBoss cache service stopped");
            return;
        }
        
        logger.fatal("This class needs to be called with 'start' or 'stop' as an argument!");
        throw new Exception("Insufficient args");
    }
    
    private static void setCacheInJNDI(TreeCache tc)
    {
        try
        {
            // these settings ought to come from a config file somewhere
            // these are app server and deployment specific!!
            
            // Using WebLogic specific classes to be able to bind to JNDI from
            // outside the container.
            
            //Environment env = new Environment();
            //env.setSecurityPrincipal("weblogic");
            //env.setSecurityCredentials("password");

            Context ctx = new InitialContext(getContextProperties());
	        ctx.bind(JNDI_NAME, tc);
        }
        catch (Exception e)
        {
            logger.error("Problems Saving Cache In JNDI", e);
            e.printStackTrace();
        }
        
    }
    
    private static TreeCache getCacheFromJNDI()
    {
        try
        {
            // these settings ought to come from a config file somewhere
            // these are app server and deployment specific!!
            
            // Using WebLogic specific classes to be able to bind to JNDI from
            // outside the container.
            
            //Environment env = new Environment();
            //env.setSecurityPrincipal("weblogic");
            //env.setSecurityCredentials("password");

            Context ctx = new InitialContext();
	        return (TreeCache) ctx.lookup(JNDI_NAME);
        }
        catch (Exception e)
        {
            logger.error("Problems looking up cache in JNDI", e);
            e.printStackTrace();
            return null;
        }
        
    }

	private static Hashtable getContextProperties(){
		Hashtable ht = new Hashtable();
		  //turn on binding replication
		ht.put(WLContext.REPLICATE_BINDINGS, "false");
		return ht;
	}

}
