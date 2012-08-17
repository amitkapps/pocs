package JGroups;

import org.jgroups.ChannelException;
import org.jgroups.JChannel;
import org.jgroups.blocks.ReplicatedHashMap;

import java.io.File;

/**
 * Hello world!
 */
public class ReplicatedHashmapFactory {

    ReplicatedHashMap<String, String> map = null;
    String jgroupsConfigFilePath = "C:\\AmitK\\work\\project\\poc\\Projects\\JGroups\\src\\main\\resources\\JGroups-config.xml";

    ReplicatedHashmapFactory(String clusterName) throws ChannelException {
        JChannel channel = new JChannel(new File(jgroupsConfigFilePath));
        channel.connect(clusterName);

        map = new ReplicatedHashMap<String, String>(channel, false);
        map.setBlockingUpdates(true);
        map.start(10000);
    }

    public ReplicatedHashMap<String, String> getMap() {
        return map;
    }
}
