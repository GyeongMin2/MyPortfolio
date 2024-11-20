import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShowPost {
    Connection connection = DbConnection.getConnection();
    WritePost writePost = new WritePost();
}
