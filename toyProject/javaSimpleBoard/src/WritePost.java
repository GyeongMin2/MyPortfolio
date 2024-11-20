import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class WritePost {
    Connection connection = DbConnection.getConnection();
    Scanner sc = new Scanner(System.in);
    PageUi pageUi=new PageUi();
    String[] writeInfoArr = {"글제목 입력", "글 내용 입력"};

    public void write(String memberId) {
        String postTitle = null;
        String postText = null;
        while (true) {
            for (int i = 0; i < writeInfoArr.length; i++) {
                pageUi.writePostUi(writeInfoArr[i]); // 글쓰기 정보 출력
                String input = sc.nextLine();
                if(input.equals("이전")||input.equals("back")){
                    break;
                }else {
                    if (i == 0) {
                        postTitle = input;
                    } else if (i == 1) {
                        postText = input;
                    }
                }
            }
            if (postTitle != null && postText != null) {
                try {
                    String sql = "INSERT INTO tbl_post (user_id,post_title,post_text)VALUES(?,?,?)";
                    PreparedStatement statement = connection.prepareStatement(sql);

                    statement.setString(1, memberId);
                    statement.setString(2, postTitle);
                    statement.setString(3, postText);

                    int rowsInserted = statement.executeUpdate();

                    if (rowsInserted>0) {
                        System.out.println("글쓰기 성공~!");
                    } else {
                        System.out.println("글쓰기 실패한듯 ㅋㅋ");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }
}
