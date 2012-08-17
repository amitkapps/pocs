package poc.spring.container.ioc;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Jan 4, 2011
 * Time: 12:48:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapHoldingBean {

    Map<String, String> theMap;

    public Map<String, String> getTheMap() {
        return theMap;
    }

    public void setTheMap(Map<String, String> theMap) {
        this.theMap = theMap;
    }
}
