package DbConnection;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConnectionManager {

    public static Connection getConnection() throws SQLException {
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TSPOON");
            return ds.getConnection();
        } catch (NamingException e) {
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }
    }
    //쓰지마샘 ㅅㄱ 
    public  static Connection getDirectConnection() throws SQLException{
        String url = "jdbc:mysql://localhost:3306/TSPOON"; // DB URL
        String username = "root"; // DB 사용자명
        String password = "kgmksw101"; // DB 비밀번호

        try {
            return java.sql.DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new SQLException(e);
        }
    }
}