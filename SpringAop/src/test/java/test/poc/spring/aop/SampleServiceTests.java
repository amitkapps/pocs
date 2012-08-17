package test.poc.spring.aop;

import amitk.poc.spring.aop.SampleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: Apr 13, 2010
 * Time: 1:28:25 PM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class SampleServiceTests {

    @Autowired
    SampleService sampleService;

    @Test
    public void testSanity(){
        assertThat(sampleService, notNullValue());
    }

    @Test(expected = RuntimeException.class)
    public void testRunService(){
        sampleService.invokeService();
        sampleService.invokeExceptionThrowingService();
    }
    
}
