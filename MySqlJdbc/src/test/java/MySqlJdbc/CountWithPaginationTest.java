package MySqlJdbc;

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

import static org.hamcrest.MatcherAssert.*;

/**
 * Created by IntelliJ IDEA.
 * User: axk
 * Date: 1/27/11
 * Time: 9:24 AM
 * To change this template use File | Settings | File Templates.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("GeneratedSequenceTest-context.xml")
public class CountWithPaginationTest {

    Logger log = LoggerFactory.getLogger(CountWithPaginationTest.class);

    @Autowired
    @Qualifier("MySql")
    DataSource mySqlDs;

    @Autowired
    @Qualifier("Oracle")
    private DataSource oracleDs;

    @Test
    public void testMysqlCountWithPagination() throws SQLException {
        //http://www.arraystudio.com/as-workshop/mysql-get-total-number-of-rows-when-using-limit.html
        Connection connection = mySqlDs.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT SQL_CALC_FOUND_ROWS emp_id, emp_first_name, department FROM employee where department = ? order by emp_id desc LIMIT 2 offset 2;");
        ps.setString(1, "IT");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            log.info("Emp Id: [{}], Dept: [{}]", rs.getString("emp_id"), rs.getString("department"));
        }
        PreparedStatement ps2 = connection.prepareStatement("select found_rows();");
        ResultSet rs2 = ps2.executeQuery();
        if(rs2.next()){
            log.info("Total Count: {}", rs2.getLong(1));
        }

        connection.close();
    }

    @Test
    public void testOracleCountWithPagination() throws SQLException {
        Connection connection = oracleDs.getConnection();
        PreparedStatement ps = connection.prepareStatement("SELECT emp_id, emp_first_name, department FROM employee where department = ?");
        ps.setString(1, "HR");
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            log.info("Emp Id: [{}], Dept: [{}]", rs.getString("emp_id"), rs.getString("department"));
        }

        connection.close();
    }
}
