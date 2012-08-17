package JGroups;

import org.jgroups.ChannelException;
import org.jgroups.blocks.ReplicatedHashMap;
import org.junit.Test;

/**
 * Unit test for simple ReplicatedHashmapFactory.
 */
public class ReplicatedHashmapFactoryTest{

    @Test
    public void testStart() throws ChannelException {
        ReplicatedHashMap<String, String> hashMap = new ReplicatedHashmapFactory("cluster-1").getMap();
        ReplicatedHashMap<String, String> hashMap1 = new ReplicatedHashmapFactory("cluster-2").getMap();

        hashMap.put("key1", "value1");
        System.out.println(hashMap1);
        hashMap1.put("key2","value2");
        System.out.println(hashMap);
    }
}
