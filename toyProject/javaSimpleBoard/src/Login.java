import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Login {
    Connection connection = DbConnection.getConnection();
    String id;
    String pwd;
    Login(){}
    Login(String ID , String PWD){
        this.id = ID;
        this.pwd = PWD;
    }
    public boolean login(Member member){
        if (connection != null) {
            try {
                //쿼리
                String sql = "SELECT * FROM tbl_user_info Where user_id = ? AND user_pwd = SHA2(?,256)";
                PreparedStatement statement = connection.prepareStatement(sql);

                statement.setString(1,id);
                statement.setString(2,pwd);

                ResultSet resultSet = statement.executeQuery();

                if (resultSet.next()){
                    System.out.println("로그인 성공!");
                    member.setUserId(resultSet.getString("user_id"));
                    member.setUserName(resultSet.getString("user_name"));
                    member.updateStatus();
                    return true;
                }else {
                    System.out.println("로그인 실패!ㅋㅋ");
                    return false;
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
