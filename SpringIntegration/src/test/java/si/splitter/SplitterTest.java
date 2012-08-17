package si.splitter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Example of hamcrest matchers http://code.google.com/p/hamcrest/wiki/Tutorial


/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SplitterTest {

    Logger log = LoggerFactory.getLogger(SplitterTest.class);

    @Autowired
    ApplicationContext applicationContext;

    @Autowired
    @Qualifier("ordersXmlString")
    String ordersXmlString;


    @Test
    public void test() throws InterruptedException {
//        log.debug("-=-=-=-=-Posting XML to Jms Queue: {}", ordersXml);
//        jmsTemplate.convertAndSend(ordersXml);
        MessageChannel ordersMessageChannel = applicationContext.getBean("ordersMessageChannel", MessageChannel.class);
        ordersMessageChannel.send(MessageBuilder.withPayload(ordersXmlString).build());
        Thread.sleep(1000000);
    }

}