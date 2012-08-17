package com.matson.cas.cache.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jboss.cache.Node;
import org.jboss.cache.TreeCache;

import com.matson.cas.cache.sample.vo.MyCachedItem;


/**
 * Simple servlet that manipulates a distributed tree cache.  The servlet expects the following HTTP parameters:
 * <BR><BR>
 * command - can be one of 'add' or 'remove'.<BR>
 * node - the fully qualified name of the cache node.<BR>
 * name - the name of the object in cache (optional -  if the command is 'remove' and the object name is omitted, the entire node is removed)<BR>
 * value - the String object to be stored in cache (only used if command = 'add')<BR>
 * <BR><BR>
 * @author Manik Surtani (manik@surtani.org)
 */
public class ManipulateCache extends CacheServlet
{
    
    
    private Logger logger = Logger.getLogger(ManipulateCache.class);
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        String command = request.getParameter("command");
        String node = request.getParameter("node");
        String name = request.getParameter("name");
        String value = request.getParameter("value");
        
        logger.debug("ManipulateCache invoked with the following params:");
        logger.debug("  command ["+command+"]");
        logger.debug("  node ["+node+"]");
        logger.debug("  name ["+name+"]");
        logger.debug("  value ["+value+"]");
        
        PrintWriter out = null;
        
        
        try
        {
            TreeCache cache = getCache();
            
            if (command.equals("add"))
            {
                logger.debug("Adding an object to the cache");
                String finalNode = ROOT_NODE + (node.startsWith("/") ? "" : "/") + node;
                cache.put(finalNode, name, new MyCachedItem(name, value));
                logger.debug("Add complete");
            }
            
            if (command.equals("remove"))
            {
                if (name == null || name.equals(""))
                {
                    // remove entire node
                    logger.debug("Removing entire node " + node + " from cache");
                    cache.remove( node );
                    logger.debug("Removed node");
                }
                else
                {
                    logger.debug("Removing element " + name + " from node " + node + " in cache");
                    
                    Node n = cache.get( node );
                    
                    
                    // for some reason, calling n.remove( name ) only removes the object
                    // in a local copy of the cache, and does not replicate this change.
                    
                    // the only way I've found to replicate the change is to remove the
                    // entire node, make changes to it, and add it again.
                    
                    n.remove( name );
                    // this map contains all the orig data, minus the object just removed above.
                    Map data = n.getData();
                    
                    // remove the entire node from cache
                    cache.remove( node );
                    
                    // and add it again with the updated data.
                    cache.put( node, data );
                    
                    logger.debug("Removed item from node");
                }
            }
            
            // Prepare response.
            out = response.getWriter();
            response.setContentType("text/html");
            out.write("ManipulateCache completed your task successfully.<BR><BR><BR>Click <A HREF='index.jsp'>here</A> to go back to the main landing page.");
        }
        catch (Exception e)
        {
            response.setContentType("text/html");
            out.write("Caught exception!  Stack trace follows.<BR><BR><BR>Click <A HREF='index.jsp'>here</A> to go back to the main landing page.<BR><BR>");
            e.printStackTrace( out );
            logger.error("Execution errs", e);            
        }
    }    
}