package MySqlJdbc;


import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//Example of hamcrest matchers http://code.google.com/p/hamcrest/wiki/Tutorial
import javax.sql.DataSource;

import java.sql.*;

import static org.hamcrest.MatcherAssert.*;

/**
 * Unit test for simple App.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
//@Transactional(readOnly = true)
public class GeneratedSequenceTest {

    Logger log = LoggerFactory.getLogger(GeneratedSequenceTest.class);

    @Autowired
    @Qualifier("MySql")
    DataSource mySqlDs;

    @Autowired
    @Qualifier("Oracle")
    private DataSource oracleDs;

    @Test
    public void testWithMySql() throws SQLException {
        assertThat("Should be true", mySqlDs, Matchers.<Object>notNullValue());

        insertIntoEmployee(mySqlDs);
        insertIntoEmployee(oracleDs);

    }

    private void insertIntoEmployee(DataSource ds) throws SQLException {
        try {
            Connection connection = ds.getConnection();
            DatabaseMetaData metaData = connection.getMetaData();
            log.info("DB Product {}", metaData.getDatabaseProductName());

            PreparedStatement ps = connection.prepareStatement("insert into employee(emp_first_name, emp_last_name) values('John', 'Doe')", new String[]{"emp_id", "join_date"});
            ps.execute();
            //Works for both Oracle 10202 & 11 driver versions
            ResultSet generatedKeys = ps.getGeneratedKeys();
            while(generatedKeys.next()){
                log.info("Result: {} for {}",generatedKeys.getLong(1), generatedKeys.getMetaData().getColumnName(1));
                //Mysql does not return the default values but Oracle does
                log.info("Result: {} for {}",generatedKeys.getTimestamp(2), generatedKeys.getMetaData().getColumnName(2));

            }

            connection.close();
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
    }

}