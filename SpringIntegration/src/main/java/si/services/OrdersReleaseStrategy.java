package si.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 8/24/11
 * Time: 1:01 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("ordersReleaseStrategy")
public class OrdersReleaseStrategy {
    Logger log = LoggerFactory.getLogger(OrdersReleaseStrategy.class);
    public boolean releaseChecker(List<Message<?>> messages) {

        log.trace("releasing orders: {}", messages);
        boolean isRelease = false;
        return isRelease;
    }
}
