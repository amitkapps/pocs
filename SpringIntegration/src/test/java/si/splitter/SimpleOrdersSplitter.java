package si.splitter;

import org.springframework.integration.Message;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 8/29/11
 * Time: 7:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleOrdersSplitter {


    private String delimiter;

    public SimpleOrdersSplitter(String delimiter) {

        this.delimiter = delimiter;
    }

    public Object split(Message<?> message) {
        String payload = (String) message.getPayload();
        return Arrays.asList(payload.split(delimiter));
    }
}
