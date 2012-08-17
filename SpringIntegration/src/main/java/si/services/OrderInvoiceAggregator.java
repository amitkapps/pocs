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
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("orderInvoiceAggregator")
public class OrderInvoiceAggregator {

    Logger log = LoggerFactory.getLogger(OrderInvoiceAggregator.class);

    public String aggregate(List<Message> messages){

        Object orderId = null;
        Object invoiceId = null;

        for(Message message : messages){
            if(null == orderId)
                orderId = message.getHeaders().get("orderId");
            if(null == invoiceId)
                invoiceId = message.getHeaders().get("invoiceId");
        }

        return "PROCESSED: OrderId " + orderId + ", InvoiceId " + invoiceId;
    }
}
