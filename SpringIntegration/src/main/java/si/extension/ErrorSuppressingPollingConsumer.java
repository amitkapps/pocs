/*
 * Copyright 2002-2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package si.extension;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.integration.Message;
import org.springframework.integration.MessagingException;
import org.springframework.integration.core.MessageHandler;
import org.springframework.integration.core.PollableChannel;
import org.springframework.integration.endpoint.AbstractPollingEndpoint;
import org.springframework.util.Assert;

/**
 * Message Endpoint that connects any {@link MessageHandler} implementation
 * to a {@link PollableChannel}.
 * 
 * @author Mark Fisher
 * @author Oleg Zhurakousky
 */
public class ErrorSuppressingPollingConsumer extends AbstractPollingEndpoint {

	private final Log logger = LogFactory.getLog(this.getClass());
	
	private final PollableChannel inputChannel;

	private final MessageHandler handler;

	private volatile long receiveTimeout = 1000;
	
	public ErrorSuppressingPollingConsumer(PollableChannel inputChannel, MessageHandler handler) {
		Assert.notNull(inputChannel, "inputChannel must not be null");
		Assert.notNull(handler, "handler must not be null");
		this.inputChannel = inputChannel;
		this.handler = handler;
	}


	public void setReceiveTimeout(long receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}

	@Override
	protected boolean doPoll() {
		Message<?> message = (this.receiveTimeout >= 0)
				? this.inputChannel.receive(this.receiveTimeout)
				: this.inputChannel.receive();
		if (this.logger.isDebugEnabled()){
			this.logger.debug("Poll resulted in Message: " + message);
		}
		if (message == null) {
			if (this.logger.isDebugEnabled()){
				this.logger.debug("Received no Message during the poll, returning 'false'");
			}
			return false;
		}
        try {
            this.handler.handleMessage(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
	}
}
