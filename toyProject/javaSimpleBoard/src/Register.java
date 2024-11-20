import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Register {
    Connection connection = DbConnection.getConnection();
    String id;
    String pwd;
    String user_name;

    Register() {
    }

    Register(String ID, String PWD, String NAME) {
        this.id = ID;
        this.pwd = PWD;
        this.user_name = NAME;
    }

    public boolean registration() {
        if (connection != null) {
            try {
                String validateId = "SELECT * FROM tbl_user_info Where user_id = ?";
                PreparedStatement valstatement = connection.prepareStatement(validateId);
                valstatement.setString(1, id);
                ResultSet valresultSet = valstatement.executeQuery();
                if (valresultSet.next()) {
                    System.out.println("아이디 중복 ㅅㄱ");
                    return false;
                } else {
                    try {
                        String sql = "INSERT INTO tbl_user_info (user_id,user_pwd,user_name)VALUES(?,sha2(?,256),?)";
                        PreparedStatement statement = connection.prepareStatement(sql);

                        statement.setString(1, id);
                        statement.setString(2, pwd);
                        statement.setString(3, user_name);

                        int rowsInserted = statement.executeUpdate();

                        if (rowsInserted>0) {
                            System.out.println("회원가입 성공한듯");
                            return true;
                        } else {
                            System.out.println("회원가입 실패한듯 ㅋㅋ");
                            return false;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("db연결안됨 ㅅㄱ");
            return false;
        }
    }
}
