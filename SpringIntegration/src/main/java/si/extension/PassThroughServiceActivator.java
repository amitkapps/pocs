package si.extension;

import org.springframework.integration.Message;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 8/24/11
 * Time: 10:56 AM
 * To change this template use File | Settings | File Templates.
 */
@Service("passthrough")
public class PassThroughServiceActivator {

    public Message<?> passthrough(Message<?> message) {
		return message;
	}
}
