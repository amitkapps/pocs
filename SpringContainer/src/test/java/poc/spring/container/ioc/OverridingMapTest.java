package poc.spring.container.ioc;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Example of hamcrest matchers http://code.google.com/p/hamcrest/wiki/Tutorial
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class OverridingMapTest {

    Logger log = LoggerFactory.getLogger(OverridingMapTest.class);

    @Autowired
    MapHoldingBean mapHoldingBean;

    @Test
    public void test() {
        assertThat(mapHoldingBean, Matchers.<Object>notNullValue());
        assertThat(mapHoldingBean.getTheMap(),  hasEntry("key1", "value1"));
    }

}