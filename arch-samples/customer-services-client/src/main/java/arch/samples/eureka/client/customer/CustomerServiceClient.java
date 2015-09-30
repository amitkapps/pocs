package arch.samples.eureka.client.customer;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CustomerServiceClient {
    Logger logger = LoggerFactory.getLogger(CustomerServiceClient.class);
    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate){
        logger.info("Setting rest template {}", restTemplate);
        this.restTemplate = restTemplate;
    }

    @HystrixCommand()
    public Customer getCustomerById(int customerId) {
        logger.info("Retrieving customer");

        Customer customer = restTemplate.getForObject("http://customer-service/customer/{customerId}", Customer.class, customerId);
        logger.info("{}", customer);
        return customer;

    }
}
