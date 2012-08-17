package si.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Header;
import org.springframework.integration.annotation.Payload;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.BatchUpdateException;
import java.sql.SQLException;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 8/19/11
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
@Component(value = "orderInvoiceService")
public class OrderInvoiceService {

    @Autowired
    SimpleJdbcTemplate orderJdbcTemplate;

    @Transactional(propagation = Propagation.MANDATORY)
    public String invoice(@Header(value = "orderId", required = true) Integer orderId, @Payload String order) throws OrderServiceException, SQLException {

        orderJdbcTemplate.update("insert into poc_order_mt(order_id, name) values (?, ?)", orderId, "NAME");

        return order + "<!--invoiced-->";
    }
}
