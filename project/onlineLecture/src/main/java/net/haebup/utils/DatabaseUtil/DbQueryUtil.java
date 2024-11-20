package net.haebup.utils.DatabaseUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
// import java.sql.Statement;

public class DbQueryUtil implements AutoCloseable {

    private PreparedStatement pstmt;
    private ResultSet rs;
    
    /*
     *     
     * // 로그인을 위한 회원 정보 조회
    public MemberDTO getMemberForLogin(String userId) throws SQLException {
        String sql = "SELECT userId, password, salt FROM tbl_member WHERE userId = ? AND member_status != 'N'";
        MemberDTO member = null;

        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[] { userId })) {

            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                member = new MemberDTO();
                member.setUserId(rs.getString("userId"));
                member.setPassword(rs.getString("password"));
                member.setSalt(rs.getString("salt"));
            }
        } catch (SQLException e) {
            throw new SQLException("사용자 정보 조회 중 오류가 발생했습니다: " + e.getMessage());
        }

        return member;
    }
        이런식으로 쓰면됨 완전편하쥬?
    */
    // 파라미터 있는 쿼리 문자열 배열 파라미터 받음
    public DbQueryUtil(Connection conn, String sql, String[] parameters) throws SQLException {
        this.pstmt = conn.prepareStatement(sql);

        // 파라미터 binding
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setString(i + 1, parameters[i]);
            }
        }
    }

    // 파라미터 있는 쿼리 Object 배열 파라미터 받음 (String || Integer)
    //파일 업로드 시 파일 사이즈 받아오기 위해 Long 타입 추가
    //null 처리 추가를 위해 다른파라미터들을 setObject로 변경후 setNull 추가 :(
    //null 이 들어올수도 있으면 이거쓰면됨 
    // ["강경민" , "제목" , "내용"] 
    public DbQueryUtil(Connection conn, String sql, Object[] parameters) throws SQLException {
        this.pstmt = conn.prepareStatement(sql);
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i] != null) {
                    pstmt.setObject(i + 1, parameters[i]);
                } else {
                    pstmt.setNull(i + 1, java.sql.Types.NULL);
                }
            }
        }
    }
    
    

    // 페이징용 또는 정수 필요하면  정수 배열 파라미터 받음
    public DbQueryUtil(Connection conn, String sql, int[] parameters) throws SQLException {
        this.pstmt = conn.prepareStatement(sql);

        // 파라미터 binding
        if (parameters != null) {
            for (int i = 0; i < parameters.length; i++) {
                pstmt.setInt(i + 1, parameters[i]);
            }
        }
    }

    public DbQueryUtil(Connection conn, String sql) throws SQLException {
        this.pstmt = conn.prepareStatement(sql);
    }

    public ResultSet executeQuery() throws SQLException {
        this.rs = pstmt.executeQuery();
        return rs;
    }

    public int executeUpdate() throws SQLException {
        return pstmt.executeUpdate();
    }

    public ResultSet getGeneratedKeys() throws SQLException {
        return pstmt.getGeneratedKeys();
    }


    @Override
    public void close() throws SQLException {
        if (rs != null) {
            rs.close();
        }
        if (pstmt != null) {
            pstmt.close();
        }
    }
}
