package poc.spring.container.ioc;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Example of hamcrest matchers http://code.google.com/p/hamcrest/wiki/Tutorial
import java.util.Map;

import static org.hamcrest.MatcherAssert.*;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SampleSpringWiring {

    Logger log = LoggerFactory.getLogger(SampleSpringWiring.class);

    @Autowired
    SampleSpringBean sampleSpringBean;

    @Test
    public void test() {
        assertThat(sampleSpringBean, Matchers.<Object>notNullValue());
    }

}