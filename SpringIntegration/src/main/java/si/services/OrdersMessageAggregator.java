package si.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.stereotype.Component;
import weblogic.xml.jaxr.registry.infomodel.OrganizationImpl;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 8/24/11
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("ordersMessageAggregator")
public class OrdersMessageAggregator {

    Logger log = LoggerFactory.getLogger(OrdersMessageAggregator.class);

    public String aggregate(List<Message> messages){
        Object ordersMessageId = messages.get(0).getHeaders().get("ordersMessageId");
        log.trace("Orders Done: {}", ordersMessageId);
        return "Completed Processing Orders from ordersMessageId-" + ordersMessageId + "###";
    }
}
