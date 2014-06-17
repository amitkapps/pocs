package poc.amitk.springbatch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class UserRowMapper implements RowMapper<User> {

    public User mapRow(ResultSet rs, int rowNum) throws SQLException {

        User user = new User();

        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setBirthDate(rs.getDate("birth_dt"));
        user.setPhoneNumber(rs.getString("phone_number"));
        user.setSalary(rs.getInt("salary"));
        user.setAge(rs.getInt("age"));

        return user;
    }

}