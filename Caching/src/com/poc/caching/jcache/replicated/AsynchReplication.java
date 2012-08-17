/*
 *
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

package com.poc.caching.jcache.replicated;


import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.framework.Assert;
import org.jboss.cache.PropertyConfigurator;
import org.jboss.cache.TreeCache;
import org.jboss.cache.Fqn;
import org.jboss.cache.TreeCacheMBean;
import org.jboss.cache.misc.TestingUtil;

import com.shared.utils.Log4JUtil;

import javax.transaction.TransactionManager;

/**
 * Unit test for replicated async TreeCache. Use locking and multiple threads to test
 * concurrent access to the tree.
 *
 * @version $Revision: 2822 $
 */
public class AsynchReplication extends TestCase {
   TreeCache cache1, cache2;
   String props=null;

   public AsynchReplication(String name) {
      super(name);
   }

   public void setUp() throws Exception {
      super.setUp();

      //Log4JUtil.addConsoleAppenderToRootForThisTask();
      
      log("creating cache1");
      cache1=createCache("CacheGroup");

      log("creating cache2");
      cache2=createCache("CacheGroup");

   }

   private TreeCache createCache(String name) throws Exception {
      TreeCache tree=new TreeCache();
      PropertyConfigurator config=new PropertyConfigurator();
      config.configure(tree, "conf/replAsync-service.xml"); // read in generic replAsync xml
      tree.setClusterName(name);
      
      // Call the hook that allows mux integration
      configureMultiplexer(tree);
      
      tree.createService();
      tree.startService();
      
      validateMultiplexer(tree);
      
      return tree;
   }
   
   /**
    * Provides a hook for multiplexer integration. This default implementation
    * is a no-op; subclasses that test mux integration would override
    * to integrate the given cache with a multiplexer.
    *
    * param cache a cache that has been configured but not yet created.
    */
   protected void configureMultiplexer(TreeCacheMBean cache) throws Exception
   {
      // default does nothing
   }
   
   /**
    * Provides a hook to check that the cache's channel came from the
    * multiplexer, or not, as expected.  This default impl asserts that
    * the channel did not come from the multiplexer.
    * 
    * @param cache a cache that has already been started
    */
   protected void validateMultiplexer(TreeCacheMBean cache)
   {
      assertFalse("Cache is not using multiplexer", cache.isUsingMultiplexer());
   }

   public void tearDown() throws Exception {
      super.tearDown();
      if(cache1 != null) {
         log("stopping cache1");
         cache1.stopService();
      }

      if(cache2 != null) {
         log("stopping cache2");
         cache2.stopService();
      }
   }


   public void testTxCompletion() throws Exception
   {
       // test a very simple replication.
       Fqn fqn = Fqn.fromString("/a");
       String key = "key";

       cache1.put(fqn, key, "value1");
       // allow for replication
       TestingUtil.sleepThread((long)500);
       Assert.assertEquals("value1", cache1.get(fqn, key));
       Assert.assertEquals("value1", cache2.get(fqn, key));

       TransactionManager mgr = cache1.getTransactionManager();
       mgr.begin();

       cache1.put(fqn, key, "value2");
       Assert.assertEquals("value2", cache1.get(fqn, key));
       Assert.assertEquals("value1", cache2.get(fqn, key));

       mgr.commit();

       TestingUtil.sleepThread((long)500);

       Assert.assertEquals("value2", cache1.get(fqn, key));
       Assert.assertEquals("value2", cache2.get(fqn, key));

       mgr.begin();
       cache1.put(fqn, key, "value3");
       Assert.assertEquals("value3", cache1.get(fqn, key));
       Assert.assertEquals("value2", cache2.get(fqn, key));

       mgr.rollback();

       TestingUtil.sleepThread((long)500);

       Assert.assertEquals("value2", cache1.get(fqn, key));
       Assert.assertEquals("value2", cache2.get(fqn, key));

       if (cache1 != null)
       {
           cache1.stopService();
           cache1 = null;
       }

       if (cache2 != null)
       {
           cache2.stopService();
           cache2 = null;
       }

   }

   public void testPutShouldNotReplicateToDifferentCluster() {
      TreeCache cache3=null;
      try {
         cache3=createCache("DifferentGroup");
         cache1.put("/a/b/c", "age", new Integer(38));
         // because we use async repl, modfication may not yet have been propagated to cache2, so
         // we have to wait a little
          TestingUtil.sleepThread((long)1000);
          assertNull("Should not have replicated", cache3.get("/a/b/c", "age"));
      }
      catch(Exception e) {
         fail(e.toString());
      }
      finally {
         if(cache3 != null)
            cache3.stopService();
      }
   }

   public void testStateTransfer() {
      TreeCache cache4=null;
      try {
         cache1.put("a/b/c", "age", new Integer(38));
         cache4=createCache("CacheGroup");
          System.out.println("" + cache4.getMembers());
         assertEquals(3, cache4.getMembers().size()); // cache1, cache2 and cache4
         assertEquals("\"age\" should be 38", new Integer(38), cache4.get("/a/b/c", "age"));
      }
      catch(Exception e) {
         fail(e.toString());
      }
      finally {
         if(cache4 != null) {
            System.out.println("cache4's view: " + cache4.getMembers());
            cache4.stopService();
         }
      }
   }


   public void testAsyncReplDelay() {
      Integer age;

      try {
         cache1.put("/a/b/c", "age", new Integer(38));

         // value on cache2 may be 38 or not yet replicated
         age=(Integer)cache2.get("/a/b/c", "age");
         log("attr \"age\" of \"/a/b/c\" on cache2=" + age);
         assertTrue("should be either null or 38", age == null || age.intValue() == 38);
      }
      catch(Exception e) {
         fail(e.toString());
      }
   }

   public void testAsyncReplTxDelay() {
      Integer age;

      try {
         TransactionManager tm = cache1.getTransactionManager();
         tm.begin();
         cache1.put("/a/b/c", "age", new Integer(38));
         tm.commit(); 

         // value on cache2 may be 38 or not yet replicated
         age=(Integer)cache2.get("/a/b/c", "age");
         log("attr \"age\" of \"/a/b/c\" on cache2=" + age);
         assertTrue("should be either null or 38", age == null || age.intValue() == 38);
      }
      catch(Exception e) {
         fail(e.toString());
      }
   }

   public void testSyncRepl() throws Exception {
      Fqn fqn = Fqn.fromString("/JSESSIONID/1010.10.5:3000/1234567890/1");
      Integer age;
      cache1.setCacheMode(TreeCache.REPL_SYNC);
      cache1.setSyncCommitPhase(true);
      cache2.setCacheMode(TreeCache.REPL_SYNC);
      cache2.setSyncCommitPhase(true);


      try {
         cache1.put(fqn, "age", new Integer(38));

         // value on cache2 must be 38
         age=(Integer)cache2.get(fqn, "age");
         assertNotNull("\"age\" obtained from cache2 is null ", age);
         assertTrue("\"age\" must be 38", age.intValue() == 38);
      }
      catch(Exception e) {
          e.printStackTrace();
         fail(e.toString());
      }
   }

   public void testSyncReplSimple() throws Exception {
      cache1.setCacheMode(TreeCache.REPL_SYNC);
      cache1.setSyncCommitPhase(true);
      cache2.setCacheMode(TreeCache.REPL_SYNC);
      cache2.setSyncCommitPhase(true);

      try {
         cache1.put("/a", "x", "y");
         assertEquals("y", cache2.get("/a", "x"));
      }
      catch(Exception e) {
         fail(e.toString());
      }
   }


    void log(String msg) {
       System.out.println("-- [" + Thread.currentThread() + "]: " + msg);
    }


   public static Test suite() {
      return new TestSuite(AsynchReplication.class);
   }

   public static void main(String[] args) {
      junit.textui.TestRunner.run(suite());
   }
}
