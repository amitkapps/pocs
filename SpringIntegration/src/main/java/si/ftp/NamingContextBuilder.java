package si.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.stereotype.Component;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 5/12/11
 * Time: 5:35 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class NamingContextBuilder {

    public static Logger log = LoggerFactory.getLogger(NamingContextBuilder.class);

    @Autowired
    public NamingContextBuilder(DataSource dataSource) throws NamingException {
        log.info("Building context and attaching datasource:{}", dataSource);
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        builder.bind("abc", dataSource);
        builder.activate();
    }
}
