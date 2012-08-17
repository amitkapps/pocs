package si.ftp;

import com.matson.cas.service.ftp.FtpBizException;
import com.matson.cas.service.ftp.FtpProxyGetterBiz;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.naming.NamingException;
import javax.sql.DataSource;
//Example of hamcrest matchers http://code.google.com/p/hamcrest/wiki/Tutorial

//import static org.mockito.Mockito.*;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("../../../main/resources/db-context.xml")
public class CasFtpTest {

    public static Logger log = LoggerFactory.getLogger(CasFtpTest.class);

    @Autowired
    DataSource dataSource;

    private void buildNamingContext() throws NamingException {
        SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
        builder.bind("abc", dataSource);
        builder.activate();

    }

    @Test
    public void getFtpFile() throws FtpBizException, NamingException {
        buildNamingContext();
        String fileText = new FtpProxyGetterBiz().getFileText(1405, "message.txt", "ftp");
        log.info("File Text: {}", fileText);

    }

}