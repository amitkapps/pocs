package com.matson.cas.cache.sample.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jboss.cache.Node;
import org.jboss.cache.TreeCache;


/**
 * Servlet that dumps the contents of a TreeCache
 * 
 * @author Manik Surtani (manik@surtani.org)
 */
public class ViewCacheContents extends CacheServlet
{
    private Logger logger = Logger.getLogger(ViewCacheContents.class);
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        PrintWriter out = response.getWriter();

        try
        {
            response.setContentType("text/html");
            out.write("<TABLE SIZE=100% BORDER=1 CELLPADDING=1><TR><TD><B>Node</B></TD> <TD><B>Name</B></TD> <TD><B>Value</B></TD></TR>\n");
            
            TreeCache cache = getCache();
            Node root = cache.get(ROOT_NODE);
            
            // print each node out as a series of HTML table rows
            if (root != null) processNode( root, out );

            out.write("</TABLE>");
	        out.write("ViewCacheContents completed your task successfully.<BR><BR><BR>Click <A HREF='index.jsp'>here</A> to go back to the main landing page.");
        }
        catch (Exception e)
        {
            out.write("Caught exception!  Stack trace follows.<BR><BR><BR>Click <A HREF='index.jsp'>here</A> to go back to the main landing page.<BR><BR>");
            e.printStackTrace(out);
        }
    }    
    
    private void processNode( Node n, PrintWriter out )
    {
        // print the contents of this node:
        Map data = n.getData();
        if (data != null && data.size() > 0)
        {
            Iterator keys = data.keySet().iterator();
            while (keys.hasNext())
            {
                String key = keys.next().toString();
                String value = data.get( key ).toString();
                out.write("<TR><TD>" + n.getFqn() + " <A HREF='ManipulateCache?command=remove&node="+ n.getFqn() + "'>(remove node)</A></TD> <TD>" + key + " <A HREF='ManipulateCache?command=remove&node="+ n.getFqn() + "&name="+ key +"'>(remove entry)</A></TD> <TD>" + value + "</TD></TR>\n");
            }
        }

        // then go into it's children recursively
        Map children = n.getChildren();
        if (children != null && children.size() > 0)
        {
	        Iterator childrenIt = children.keySet().iterator();
	        while (childrenIt.hasNext())
	        {
	            String key = childrenIt.next().toString();
	            Node value = (Node) children.get( key );
	            
	            processNode( value, out );
	        }
        }
    }
}
