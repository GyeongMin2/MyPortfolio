//email 미구현
package dao;

import dto.member.MemberDTO;
import util.DateUtil;
import DbConnection.DBConnectionManager;
import DbConnection.DbQueryUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {

    // 회원 등록
    public void insertMember(MemberDTO member) throws SQLException {
        String sql = "INSERT INTO tbl_member (userId, password, salt, name, birthday, gender, phone, interest, grade, location_agreement, thirdparty_agreement, promotion_agreement, chunjaeEdu_agreement, member_status, reg_date) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] {
                        member.getUserId(),
                        member.getPassword(),
                        member.getSalt(),
                        member.getName(),
                        member.getBirthday(),
                        member.getGender(),
                        member.getPhone(),
                        member.getInterest(),
                        member.getGrade(),
                        member.getLocationAgreement(),
                        member.getThirdpartyAgreement(),
                        member.getPromotionAgreement(),
                        member.getChunjaeEduAgreement(),
                        member.getMemberStatus()
                })) {

            dbUtil.executeUpdate();

        } catch (SQLException e) {
            throw new SQLException("회원 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 특정 userId로 회원 모든컬럼 조회
    public MemberDTO getMemberByUserId(String userId) throws SQLException {
        String sql = "SELECT * FROM tbl_member WHERE userId = ?";
        MemberDTO member = null;

        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[] { userId })) {

            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                // Email 미구현 컬럼만 있음
                member = new MemberDTO();
                member.setUserId(rs.getString("userId"));
                member.setPassword(rs.getString("password"));
                member.setSalt(rs.getString("salt"));
                member.setName(rs.getString("name"));
                member.setBirthday(rs.getString("birthday"));
                member.setGender(rs.getString("gender"));
                member.setPhone(rs.getString("phone"));
                member.setInterest(rs.getString("interest"));
                member.setGrade(rs.getString("grade"));
                member.setLocationAgreement(rs.getInt("location_agreement"));
                member.setThirdpartyAgreement(rs.getInt("thirdparty_agreement"));
                member.setPromotionAgreement(rs.getInt("promotion_agreement"));
                member.setChunjaeEduAgreement(rs.getInt("chunjaeEdu_agreement"));
                member.setMemberStatus(rs.getString("member_status"));
                member.setRegDate(rs.getString("reg_date"));
            }

        } catch (SQLException e) {
            throw new SQLException("회원 정보를 조회하는 중 오류가 발생했습니다: " + e.getMessage());
        }

        return member;
    }

    // 아이디 중복 확인
    public boolean isUserIdAvailable(String userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tbl_member WHERE userId = ?";
        boolean isAvailable = false;

        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[] { userId })) {

            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                isAvailable = (count == 0);
            } else {
                isAvailable = false;
            }
        }
        return isAvailable;
    }

    // 로그인을 위한 회원 정보 조회
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

    // 이름과 전화번호로 아이디 찾기
    public List<MemberDTO> getMembersByNameAndPhone(String name, String phone) throws SQLException {
        // userId , name, phone, interest, reg_date
        String sql = "SELECT userId, name, phone, interest, reg_date FROM tbl_member WHERE name = ? AND phone = ? AND member_status = 'Y'";
        List<MemberDTO> members = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[] { name, phone })) {

            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                MemberDTO member = new MemberDTO();
                member.setUserId(rs.getString("userId"));
                member.setName(rs.getString("name"));
                member.setPhone(rs.getString("phone"));
                member.setInterest(rs.getString("interest"));

                // Format the reg_date
                String regDateStr = rs.getString("reg_date");
                LocalDateTime regDate = DateUtil.stringToLocalDateTime(regDateStr);
                String formattedRegDate = regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                member.setRegDate(formattedRegDate);

                members.add(member);
            }

        } catch (SQLException e) {
            throw new SQLException("회원 정보를 조회하는 중 오류가 발생했습니다: " + e.getMessage());
        }

        return members;
    }

    // 아이디, 이름, 전화번호로 회원 조회
    public MemberDTO getMemberByUserIdNamePhone(String userId, String name, String phone) throws SQLException {
        String sql = "SELECT * FROM tbl_member WHERE userId = ? AND name = ? AND phone = ? AND member_status = 'Y'";
        MemberDTO member = null;

        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, name, phone })) {

            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                member = new MemberDTO();
                member.setUserId(rs.getString("userId"));
                // 필요한 다른 필드들도 설정
            }
        } catch (SQLException e) {
            throw new SQLException("회원 정보를 조회하는 중 오류가 발생했습니다: " + e.getMessage());
        }

        return member;
    }

    // 회원 정보 수정
    public void updateMember(MemberDTO member) throws SQLException {
        String sql = "UPDATE tbl_member SET password = ?, salt = ?, name = ?, birthday = ?, " +
                "gender = ?, phone = ?, interest = ?, grade = ?, location_agreement = ?, " +
                "thirdparty_agreement = ?, promotion_agreement = ?, chunjaeEdu_agreement = ?, " +
                "member_status = ? WHERE userId = ?";

        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] {
                        member.getPassword(),
                        member.getSalt(),
                        member.getName(),
                        member.getBirthday(),
                        member.getGender(),
                        member.getPhone(),
                        member.getInterest(),
                        member.getGrade(),
                        member.getLocationAgreement(),
                        member.getThirdpartyAgreement(),
                        member.getPromotionAgreement(),
                        member.getChunjaeEduAgreement(),
                        member.getMemberStatus(),
                        member.getUserId()
                })) {

            int rowsAffected = dbUtil.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("회원 정보 수정에 실패했습니다. 해당 사용자를 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            throw new SQLException("회원 정보를 수정하는 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 회원 삭제 (필드를 빈 값으로 바꾸고 삭제 테이블에 삽입) 이메일 미구현
    public void deleteMember(String userId) throws SQLException {
        String insertSQL = "INSERT INTO tbl_deleted_member (userId, password, salt, name, birthday, gender, phone, interest, grade, location_agreement, thirdparty_agreement, promotion_agreement, chunjaeEdu_agreement, member_status, reg_date) SELECT userId, password, salt, name, birthday, gender, phone, interest, grade, location_agreement, thirdparty_agreement, promotion_agreement, chunjaeEdu_agreement, member_status, reg_date FROM tbl_member WHERE userId = ?";

        String updateSQL = "UPDATE tbl_member SET password = '', salt = '', name = '', birthday = NULL, gender = '', phone = '', interest = '', grade = '', location_agreement = 0, thirdparty_agreement = 0, promotion_agreement = 0, chunjaeEdu_agreement = 0, member_status = 'N' WHERE userId = ?";

        try (Connection conn = DBConnectionManager.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (DbQueryUtil insertUtil = new DbQueryUtil(conn, insertSQL, new String[] { userId })) {
                    insertUtil.executeUpdate();
                }
                try (DbQueryUtil updateUtil = new DbQueryUtil(conn, updateSQL, new String[] { userId })) {
                    updateUtil.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new SQLException("회원 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
