package si.ftp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Example of hamcrest matchers http://code.google.com/p/hamcrest/wiki/Tutorial
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
//import static org.mockito.Mockito.*;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"/db-context.xml", "/spring-integration-config.xml"})
//@Transactional(readOnly = true)
public class SpringIntegrationWithCasFtpTest {

    Logger log = LoggerFactory.getLogger(SpringIntegrationWithCasFtpTest.class);

    @Test
    public void test() {
        assertThat("Should be true", true, is(true));
    }

}