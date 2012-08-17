package si.hello;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 8/2/11
 * Time: 11:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class MyHelloService {

    public static Logger log = LoggerFactory.getLogger(MyHelloService.class);

    public String sayHello(String name){
        String message = "Hello " + name;
        log.info("Returning: {}",  message);
        return message;
    }
}
