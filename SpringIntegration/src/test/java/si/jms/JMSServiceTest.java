package si.jms;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Example of hamcrest matchers http://code.google.com/p/hamcrest/wiki/Tutorial


/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class JMSServiceTest {

    Logger log = LoggerFactory.getLogger(JMSServiceTest.class);

//    @Autowired
//    JmsTemplate jmsTemplate;

//    @Autowired
//    @Qualifier("ordersXmlString")
//    String ordersXml;

    @Test
    public void test() throws InterruptedException {
//        log.debug("-=-=-=-=-Posting XML to Jms Queue: {}", ordersXml);
//        jmsTemplate.convertAndSend(ordersXml);

        Thread.sleep(1000000);
    }

}