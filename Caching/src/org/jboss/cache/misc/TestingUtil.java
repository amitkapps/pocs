/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

package org.jboss.cache.misc;

import org.jboss.cache.Fqn;
import org.jboss.cache.TreeCache;
import org.jboss.cache.TreeCacheMBean;

import java.io.File;
import java.util.Vector;

/**
 * Utilities for unit testing JBossCache.
 * 
 * @author <a href="mailto://brian.stansberry@jboss.com">Brian Stansberry</a>
 * @version $Revision$
 */
public class TestingUtil
{

   /**
    * Kills a cache - stops it, clears any data in any cache loaders, and rolls back any associated txs
    */
   public static void killCaches(TreeCache[] caches)
   {
      if (caches == null) return;

      for (int i = 0; i<caches.length; i++)
      {
         TreeCache c = caches[i];
         if (c != null)
         {
            if (c.getTransactionManager() != null)
            {
               try
               {
                  c.getTransactionManager().rollback();
               }
               catch (Exception e)
               {
                  // don't care
               }
            }

            if (c.getCacheLoader() != null)
            {
               try
               {
                  c.getCacheLoader().remove(Fqn.ROOT);
               }
               catch (Exception e)
               {
                  // don't care
               }
            }

            c.stop();
            c.destroy();
         }
      }
   }

   /**
    * until it either returns true or <code>timeout</code> ms have elapsed.
    * 
    * @param caches     caches which must all have consistent views
    * @param timeout    max number of ms to loop
    * 
    * @throws RuntimeException  if <code>timeout</code> ms have elapse without
    *                           all caches having the same number of members.
    */
   public static void blockUntilViewsReceived(TreeCacheMBean[] caches, long timeout)
   {
      long failTime = System.currentTimeMillis() + timeout;
      
      while (System.currentTimeMillis() < failTime)
      {
         sleepThread(100);
         if (areCacheViewsComplete(caches))
            return;
      }
      
      throw new RuntimeException("timed out before caches had complete views");
   }
   
   /**
    * until it either returns true or <code>timeout</code> ms have elapsed.
    * 
    * @param caches     caches which must all have consistent views
    * @param groupSize  number of caches expected in the group
    * @param timeout    max number of ms to loop
    * 
    * @throws RuntimeException  if <code>timeout</code> ms have elapse without
    *                           all caches having the same number of members.
    */
   public static void blockUntilViewReceived(TreeCacheMBean cache, int groupSize, long timeout)
   {
      long failTime = System.currentTimeMillis() + timeout;
      
      while (System.currentTimeMillis() < failTime)
      {
         sleepThread(100);
         if (isCacheViewComplete(cache, groupSize))
            return;
      }
      
      throw new RuntimeException("timed out before caches had complete views");
   }
   
   /**
    * Checks each cache to see if the number of elements in the array
    * returned by {@link TreeCache#getMembers()} matches the size of 
    * the <code>caches</code> parameter.
    * 
    * @param caches caches that should form a View
    * @return   <code>true</code> if all caches have 
    *           <code>caches.length</code> members; false otherwise
    * 
    * @throws IllegalStateException if any of the caches have MORE view
    *                               members than caches.length
    */
   public static boolean areCacheViewsComplete(TreeCacheMBean[] caches)
   {
      int memberCount = caches.length;
      
      for (int i = 0; i < memberCount; i++)
      {
         TreeCacheMBean cache = caches[i];
         return isCacheViewComplete(cache, memberCount);
      }
      
      return true;
   }

   /**
    * 
    * @param cache
    * @param memberCount
    * @return
    */
   public static boolean isCacheViewComplete(TreeCacheMBean cache, int memberCount)
   {
      Vector members = cache.getMembers();
      if (members == null || memberCount > members.size())
      {
         return false;
      }
      else if (memberCount < members.size())
      {
         // This is an exceptional condition
         StringBuffer sb = new StringBuffer("Cache at address ");
         sb.append(cache.getLocalAddress());
         sb.append(" had ");
         sb.append(members.size());
         sb.append(" members; expecting ");
         sb.append(memberCount);
         sb.append(". Members were (");
         for (int j = 0; j < members.size(); j++)
         {
            if (j > 0)
               sb.append(", ");
            sb.append(members.elementAt(j));
         }
         sb.append(')');
         
         throw new IllegalStateException(sb.toString());
      }
      
      return true;
   }
   
   
   
   /**
    * Puts the current thread to sleep for the desired number of ms, suppressing
    * any exceptions.
    * 
    * @param sleeptime number of ms to sleep
    */
   public static void sleepThread(long sleeptime)
   {
      try
      {
         Thread.sleep(sleeptime);
      }
      catch (InterruptedException ie) {}
   }

   public static void recursiveRemove(File dir)
   {
      if (dir == null) return;
      if (!dir.isDirectory())
      {
         if (!dir.delete()) dir.deleteOnExit();
         return;
      }
      File[] files = dir.listFiles();
      if (files != null)
      {
         for (int i = 0; i < files.length; i++)
         {
            if (files[i].isDirectory())
            {
               recursiveRemove(files[i]);
            }
            else
            {
               if (!files[i].delete()) files[i].deleteOnExit();
            }
         }
      }
   }
}
