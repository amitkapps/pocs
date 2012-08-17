package amitk.poc.spring.aop;

import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Apr 13, 2010
 * Time: 1:25:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SampleService {

    Logger logger = Logger.getLogger("SampleService");

    public void invokeService(){
        logger.info("Executing service method");
    }

    public void invokeExceptionThrowingService() {
        throw new RuntimeException();
    }
}
