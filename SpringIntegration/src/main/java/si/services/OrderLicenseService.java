package si.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 8/19/11
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Component(value = "orderLicenseService")
public class OrderLicenseService {

    @Autowired
    SimpleJdbcTemplate orderJdbcTemplate;

    @Transactional(propagation = Propagation.MANDATORY)
    public String license(@Header(value = "orderId")Integer orderId, @Payload String order) throws OrderServiceException, SQLException {

        String license = "LICENSE";
        if(orderId.equals(1))
            license = "LICENSEXXXXXXX";
        orderJdbcTemplate.update("insert into poc_order_license_mt(order_id, license_id) values (?, ?)", orderId, license);

        return order + "<!--licensed-->";
    }
}
