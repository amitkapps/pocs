package arch.samples.eureka.client.customer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by amitkapps on 9/28/15.
 */
@RestController
public class CustomerServiceInvokerController {

    Logger logger = LoggerFactory.getLogger(CustomerServiceInvokerController.class);
    private CustomerServiceClient customerServiceClient;


    @Autowired
    public void setCustomerServiceClient(CustomerServiceClient customerServiceClient){
        this.customerServiceClient = customerServiceClient;
    }

    @RequestMapping("/customer/{customerId}")
    public Customer getCustomer(@PathVariable int customerId){
        return customerServiceClient.getCustomerById(customerId);
    }

}
