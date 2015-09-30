package arch.samples.customer.services;

import arch.samples.customer.models.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Services exposet
 */
@Service
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Value("${customer-services.version}")
    String version;


    public Customer getCustomerById(int customerId) {

        logger.info("version:{}", version);

        return new Customer(customerId)
                .setFirstName("John")
                .setLastName("Doe")
                .setOrganizationName("Matson Inc.");
    }
}
