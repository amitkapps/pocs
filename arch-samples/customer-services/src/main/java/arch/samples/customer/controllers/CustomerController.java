package arch.samples.customer.controllers;

import arch.samples.customer.models.Customer;
import arch.samples.customer.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.*;

/**
 * external interface to customer services
 */
@RestController
public class CustomerController {


    private final CustomerService customerService;
    private int breakingPointId = 10;


    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @RequestMapping(value = "/customer/break", method = RequestMethod.POST)
    public void setBreakingPointId(@RequestBody Customer customer){
        System.out.println("Setting breaking point " + customer);
        this.breakingPointId = customer.getId();
    }

    @RequestMapping(value = "/customer/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable int customerId) throws InterruptedException {
        System.out.println("Creating customer object " + customerId);
        if(breakingPointId == customerId)
            Thread.sleep(10000);

        return customerService.getCustomerById(customerId);
    };
}
