package si.hello;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
//Example of hamcrest matchers http://code.google.com/p/hamcrest/wiki/Tutorial
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class MyHelloServiceTest {

    Logger log = LoggerFactory.getLogger(MyHelloServiceTest.class);

    @Autowired
    @Qualifier("names")
    MessageChannel names;

    @Test
    public void test() {
        names.send( MessageBuilder.withPayload("World").build());
    }

}