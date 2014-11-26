package poc.gates.nightlybatch;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class WorkListRowMapper implements RowMapper<WorkList> {

    public WorkList mapRow(ResultSet rs, int rowNum) throws SQLException {

        WorkList workList = new WorkList();

        workList.setId(rs.getInt("id"));
        workList.setWorkName(rs.getString("work_name"));

        return workList;
    }

}