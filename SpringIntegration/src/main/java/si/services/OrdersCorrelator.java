package si.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.MessageHandlingException;
import org.springframework.integration.message.ErrorMessage;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 8/24/11
 * Time: 12:41 PM
 * To change this template use File | Settings | File Templates.
 */
@Component("ordersCorrelator")
public class OrdersCorrelator {
    Logger log = LoggerFactory.getLogger(OrdersCorrelator.class);

    public Object correlate(Message<?> message) {
        if (message instanceof ErrorMessage) {
            Message<?> failedMessage = ((MessageHandlingException) ((ErrorMessage) message).getPayload()).getFailedMessage();
            Object obj = failedMessage.getHeaders().get("ordersMessageId");
            log.info("correlating an errored out part {}", failedMessage.getHeaders());
            return obj;
        }

        Object obj = message.getHeaders().get("ordersMessageId");
        log.info("correlating successfully completed part of {}", message.getHeaders());
        return obj;
    }
}
