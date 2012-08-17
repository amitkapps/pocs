package si.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 5/12/11
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class MyHelloService {
    public static Logger log = LoggerFactory.getLogger(MyHelloService.class);

    public String sayHello(String name){
        String resultString = "Hello " + name;
        log.info("result {}", resultString);
        return resultString;
    }
}
