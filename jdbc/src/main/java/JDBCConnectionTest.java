
import java.sql.*;

/**
 * Hello world!
 *
 */
public class JDBCConnectionTest{
    public static void main( String[] args ) throws Exception{
        if(args.length < 4){
            System.out.println("USAGE: java JDBCConnectionTest <driverClass> <connectionURL> <userId> <password> [select-sql]");
            System.out.println("Example: java JDBCConnectionTest com.mysql.jdbc.Driver jdbc:mysql://10.8.7.63:3306/vmimgr vmiuser vmiuser \"select 1 from dual\"");
            System.out.println("Oracle driver: oracle.jdbc.OracleDriver conn-url: jdbc:oracle:thin:@10.201.1.26:1521:MATP01");
            System.out.println("MySql driver: com.mysql.jdbc.Driver conn-url: jdbc:mysql://10.8.7.63:3306/vmimgr");
            System.out.println("DB2 driver: com.ibm.db2.jcc.DB2Driver conn-url: jdbc:db2://10.200.2.2:446/MATSFDB2P");
            System.exit(1);
        }
        System.out.println("driverClass: " + args[0]);
        System.out.println("connectionURL: " + args[1]);
        System.out.println("userId: " + args[2]);
        System.out.println("password: " + args[3].replaceAll(".", "*"));
        String sql = (args.length > 4 && !"".equals(args[4]))?args[4]: "select 1 from dual";
        System.out.println("sql: " + sql);
//        System.out.println("driverClass: " + args[0]);
        Connection connection = null;
        try{

            connection = getConnection(args[0], args[1], args[2], args[3]);
            PreparedStatement stmt = connection.prepareStatement(sql);
            System.out.println("SQL: " + sql);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                System.out.println("Result: " + rs.getString(1));
            }
            rs.close();
        }finally {
            if(null != connection)
            connection.close();
        }
    }


    public static Connection getConnection(String driverClass, String connectionURL, String userId, String password) throws ClassNotFoundException, SQLException {
        Class.forName(driverClass);
        Connection connection = null;
        return DriverManager.getConnection(connectionURL, userId, password);

    }
}
