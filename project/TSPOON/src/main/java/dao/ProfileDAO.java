package dao;

import dto.profile.ProfilePictureDTO;
import DbConnection.DBConnectionManager;
import DbConnection.DbQueryUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileDAO {
    
    // 새 사용자 등록 시 기본 프로필 사진을 생성
    public void createDefaultProfile(String userId) throws SQLException {
        String sql = "INSERT INTO tbl_profile_picture (userId) VALUES (?)";
        
        try (Connection conn = DBConnectionManager.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[]{userId})) {
            dbUtil.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("기본 프로필 생성 중 오류 발생: " + e.getMessage());
        }
    }

    // 사용자의 프로필 사진 정보를 조회
    public ProfilePictureDTO getProfilePicture(String userId) throws SQLException {
        String sql = "SELECT * FROM tbl_profile_picture WHERE userId = ?";
        ProfilePictureDTO profile = null;

        try (Connection conn = DBConnectionManager.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[]{userId})) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                profile = new ProfilePictureDTO();
                profile.setProfileId(rs.getInt("profile_id"));
                profile.setUserId(rs.getString("userId"));
                profile.setFileName(rs.getString("file_name"));
                profile.setFilePath(rs.getString("file_path"));
                profile.setUploadDate(rs.getString("upload_date"));
                profile.setFileSize(rs.getLong("file_size"));
            }
        } catch (SQLException e) {
            throw new SQLException("프로필 사진 조회 중 오류 발생: " + e.getMessage());
        }
        return profile;
    }

    // 프로필 사진을 업데이트. 프로필이 없는 경우 새로 생성
    public void updateProfilePicture(ProfilePictureDTO profile) throws SQLException {
        String sql = "UPDATE tbl_profile_picture SET file_name = ?, file_size = ?, file_path = ?, upload_date = NOW() WHERE userId = ?";
        
        try (Connection conn = DBConnectionManager.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{
                     profile.getFileName(),
                     profile.getFileSize(),
                     profile.getFilePath(),
                     profile.getUserId()
             })) {
            int rowsAffected = dbUtil.executeUpdate();
            //회원가입시 기본 프로필 사진 생성
            if (rowsAffected == 0) {
                // 업데이트할 행이 없다면 새로운 프로필 사진을 삽입
                String insertSql = "INSERT INTO tbl_profile_picture (userId) VALUES (?)";
                try (DbQueryUtil insertDbUtil = new DbQueryUtil(conn, insertSql, new Object[]{
                        profile.getUserId(),
                })) {
                    insertDbUtil.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new SQLException("프로필 사진 업데이트 중 오류 발생: " + e.getMessage());
        }
    }

    // 프로필 사진 삭제 (NULL로 설정)
    public void deleteProfilePicture(String userId) throws SQLException {
        String sql = "UPDATE tbl_profile_picture SET file_name = NULL, file_size = NULL, file_path = NULL, upload_date = NULL WHERE userId = ?";
        
        try (Connection conn = DBConnectionManager.getConnection();
         DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[]{userId})) {
        int rowsAffected = dbUtil.executeUpdate();
        if (rowsAffected == 0) {
            throw new SQLException("삭제할 프로필 사진을 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            throw new SQLException("프로필 사진 삭제 중 오류 발생: " + e.getMessage());
        }
    }
}