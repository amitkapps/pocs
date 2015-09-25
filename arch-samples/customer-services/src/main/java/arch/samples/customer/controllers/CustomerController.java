package arch.samples.customer.controllers;

import arch.samples.customer.models.Customer;
import org.springframework.web.bind.annotation.*;

/**
 * Created by amitkapps on 9/24/15.
 */
@RestController
public class CustomerController {


    private int breakingPointId = 10;


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

        return new Customer(customerId)
                .setFirstName("John")
                .setLastName("Doe")
                .setOrganizationName("Matson Inc.");
    };
}
