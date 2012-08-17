package si.extension;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.Message;
import org.springframework.integration.core.PollableChannel;

/**
 * A channel implementation that essentially behaves like "/dev/null".
 * All receive() calls will return <em>null</em>, and all send() calls
 * will return <em>true</em> although no action is performed.
 * Note however that the invocations are logged at debug-level.
 * 
 * @author Mark Fisher
 */
public class LoggingMessageNullChannel implements PollableChannel {

	public static Logger logger = LoggerFactory.getLogger(LoggingMessageNullChannel.class);

    public boolean send(Message<?> message) {
		logger.info("payload [{}] sent to null channel, Headers: {}", message.getPayload(), message.getHeaders());
		return true;
	}

	public boolean send(Message<?> message, long timeout) {
		return this.send(message);
	}

	public Message<?> receive() {
		if (logger.isDebugEnabled()) {
			logger.debug("receive called on null channel");
		}		
		return null;
	}

	public Message<?> receive(long timeout) {
		return this.receive();
	}

}
