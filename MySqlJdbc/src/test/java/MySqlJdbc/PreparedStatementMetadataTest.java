package MySqlJdbc;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("GeneratedSequenceTest-context.xml")
public class PreparedStatementMetadataTest {

    Logger log = LoggerFactory.getLogger(PreparedStatementMetadataTest.class);
    @Autowired
    @Qualifier("MySql")
    DataSource mySqlDs;

    @Autowired
    @Qualifier("Oracle")
    private DataSource oracleDs;
    @Test
    public void testGetPreparedStatementMetadata() throws SQLException {
        MatcherAssert.assertThat(true, Matchers.is(true));

        Connection conn = mySqlDs.getConnection();
        PreparedStatement pstmt = conn.prepareStatement("select department from employee");
        ResultSet resultSet = pstmt.executeQuery();
        while (resultSet.next()){
            log.info("Dept {}", resultSet.getString("department"));
        }
        ResultSetMetaData metaData = pstmt.getMetaData();
        log.info("precision {}", metaData.getPrecision(1));


        conn.close();
    }

}
