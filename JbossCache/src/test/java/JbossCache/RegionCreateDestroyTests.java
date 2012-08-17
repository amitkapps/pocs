package JbossCache;

import org.jboss.cache.Cache;
import org.jboss.cache.DefaultCacheFactory;
import org.jboss.cache.Fqn;
import org.jboss.cache.Region;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: May 11, 2010
 * Time: 11:25:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class RegionCreateDestroyTests {



    @Test
    public void testRegionCreateDestroy(){

        Cache<Object, Object> cache = new DefaultCacheFactory<Object, Object>().createCache("LocalCache-service.xml");
        cache.create();
        cache.start();

        System.out.println(cache.getRegion(Fqn.fromString("/CAS"), true));
        cache.put("CAS", "KEY", "CASValue");
        System.out.println(cache.getRegion(Fqn.fromString("/CAS/search"), true));
        cache.put("CAS/search", "KEY", "searchValue");
        System.out.println(cache.getRegion(Fqn.fromString("/CAS/search/external"), true));
        cache.put("CAS/search/external", "KEY", "externalValue");
        System.out.println(cache.getRegion(Fqn.fromString("/CAS/search/external/10"), true));
        cache.removeRegion(Fqn.fromString("/CAS/search/external/10"));
        System.out.println(cache.getRegion(Fqn.fromString("/CAS/search/external/10/12"), false));
        //cache.removeRegion(Fqn.fromString("CAS"));
        cache.removeNode(Fqn.fromString("CAS"));
        System.out.println(cache.getRoot().getChildren());
        System.out.println(cache.getNode("CAS/search").getChildren());
/*
        System.out.println(cache.getRegion(Fqn.fromString("/CAS"), true));
        System.out.println(cache.getRegion(Fqn.fromString("/CAS"), true));
*/

    }
}
