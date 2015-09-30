package arch.samples.eureka.client;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
public class CustomerServiceClientApplication {

    static Logger logger = LoggerFactory.getLogger(CustomerServiceClientApplication.class);

    public static void main(String[] args){
        new SpringApplicationBuilder(CustomerServiceClientApplication.class).web(true).run(args);
    }

}
