package si.siqueues;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Example of hamcrest matchers http://code.google.com/p/hamcrest/wiki/Tutorial


/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class QueueTest {

    Logger log = LoggerFactory.getLogger(QueueTest.class);

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    @Qualifier("ordersXmlString")
    String ordersXml;

    @Test
    public void test() throws InterruptedException {
        log.debug("-=-=-=-=-Posting XML to Jms Queu: {}", ordersXml);
        jmsTemplate.convertAndSend(ordersXml);

        Thread.sleep(1000000);
    }

}