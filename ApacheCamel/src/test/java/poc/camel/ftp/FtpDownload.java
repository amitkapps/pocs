package poc.camel.ftp;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.ConnectionFactory;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 2/19/12
 * Time: 12:10 AM
 * To change this template use File | Settings | File Templates.
 */
public class FtpDownload {
    Logger log = LoggerFactory.getLogger(FtpDownload.class);
    
    @Test
    public void downloadFromFtp() throws Exception {
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("vm://localhost");
        CamelContext context = new DefaultCamelContext();
        context.addComponent("jms",
                JmsComponent.jmsComponentAutoAcknowledge(connectionFactory));

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("ftp://localhost/orders?username=camel&password=camel")
                    .process(new Processor() {
                        public void process(Exchange exchange) throws Exception {
                            //To change body of implemented methods use File | Settings | File Templates.
                            log.info("We just downloaded {}", exchange.getIn().getHeader("CamelFileName"));
                        }
                    })
                    .to("jms:queue:incomingOrders");
            }
        });
        context.start();
        Thread.sleep(10000);
        context.stop();


    }
}
