package infinispan;

import org.apache.lucene.queryParser.ParseException;
import org.infinispan.Cache;
import org.infinispan.config.Configuration;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.query.CacheQuery;
import org.infinispan.query.QueryFactory;
import org.infinispan.query.QueryIterator;
import org.infinispan.query.backend.QueryHelper;
 
import java.util.List;
import java.util.Properties;
 
public class InfinispanQueryTest {
 
  public void queryMyCache() throws ParseException {
     
     Configuration cfg = new Configuration(); 
     cfg.setIndexingEnabled(true);
     // set any other configuration attributes you may need
     // alternatively, you could configure this using XML
 
     Cache c = new DefaultCacheManager(cfg).getCache();
 
     // The QueryHelper must be instantiated before putting objects into the cache.
     QueryHelper qh = new QueryHelper(c, new Properties(), Person.class);
 
     // Let's say I have a separate method that puts a bunch of things in the cache as per normal.
     putPersonsInCache();
 
     // When I want to query objects in the cache, I will create a QueryFactory.
     QueryFactory qf = new QueryFactory(c, qh);
 
     // Let's say I'm searching on a field called "name" and looking for "John".     
     CacheQuery cq = qf.getBasicQuery("firstName", "John");
      //TODO: Use a Lucene query
//      qf.getQuery(luceneQuery);
 
     // Now I can put all my hits into a list!
     List found = cq.list();
 
     // I can also just get the number of hits that I have. This is cheap as it 
     // doesn't load objects from the cache.
     int hits = cq.getResultSize();
     
     // The CacheQuery interface has 2 kinds of iterators. They both implement the same interface but
     // have different implementations under the hood. One loads all hits from the cache first and the
     // other on the fly. They both implement the QueryIterator interface.
     QueryIterator eagerIterator = cq.iterator();
     QueryIterator lazyIterator = cq.lazyIterator();
 
     // From here, there are various other API methods on the interface.
     // For example, I can pick out the first and last elements of all my hits.
     eagerIterator.first();
     Object first = eagerIterator.next();
 
     lazyIterator.last();
     Object last = lazyIterator.previous();
  }
}