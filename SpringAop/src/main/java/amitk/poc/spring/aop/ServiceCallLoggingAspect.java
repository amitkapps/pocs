package amitk.poc.spring.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.logging.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Apr 13, 2010
 * Time: 2:14:21 PM
 * To change this template use File | Settings | File Templates.
 */
@Aspect
public class ServiceCallLoggingAspect {

    Logger logger = Logger.getLogger("ServiceCallLoggingAspect");

    @AfterReturning("allAppMethods()")
    public void logMethodCall(JoinPoint jp){
        logger.info("AFTER METHOD INVOKE..." + jp.getTarget().getClass().getCanonicalName());
    }

    @AfterThrowing(value = "amitk.poc.spring.aop.ServiceCallLoggingAspect.allAppMethods()", throwing = "e")
    public void logMethodCallException(JoinPoint jp, Exception e){
        logger.severe( "AFTER METHOD EXCEPTION... " + jp.getSignature().toLongString() + ", " + e);
    }


    @Pointcut("execution(* amitk.poc..*.*(..))")
    public void allAppMethods(){}
}
