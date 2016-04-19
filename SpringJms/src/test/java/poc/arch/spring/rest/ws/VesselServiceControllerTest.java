package poc.arch.spring.rest.ws;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Created by amitkapps on 11/10/15.
 */

public class VesselServiceControllerTest {

    long TERMINAL_COUNT = 10000000;

    Logger logger = LoggerFactory.getLogger("console");

    @Test
    @Ignore
    public void testWSInvocation() throws InterruptedException {
        Date start = new Date();
        RestTemplate restTemplate = new RestTemplate();

//        String url = "http://localhost:8080/springjms/rest/terminalService/{terminalId}/vesselList"; //local
        String url = "http://10.101.10.192:8080/springjms/rest/terminalService/{terminalId}/vesselList"; //dev poc

        for (int terminalId=1; terminalId <= TERMINAL_COUNT; terminalId++){
            String response = restTemplate.getForObject(url, String.class, terminalId);
            logger.info("got response for {} : {}", terminalId, response);
            if( terminalId % 5000 == 0)
                Thread.sleep(3/2*60*60*1000);
            else
                Thread.sleep(10);

        }

        logger.info("Start: {}, End: {}", start, new Date());

    }
}
