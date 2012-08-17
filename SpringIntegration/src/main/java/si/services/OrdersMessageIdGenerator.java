package si.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 8/24/11
 * Time: 10:35 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("ordersMessageIdGenerator")
public class OrdersMessageIdGenerator {

    Logger log = LoggerFactory.getLogger(OrdersMessageIdGenerator.class);

    int id = 0;

    public int getNextMessageId(String payload){
        return ++id;
    }


}
