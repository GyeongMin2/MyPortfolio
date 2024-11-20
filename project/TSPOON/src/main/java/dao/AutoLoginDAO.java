package dao;

import java.sql.*;
import java.time.LocalDateTime;
import DbConnection.DBConnectionManager;
import DbConnection.DbQueryUtil;
import util.DateUtil;

public class AutoLoginDAO {

    // 자동 로그인 토큰을 저장하는 메서드
    public void saveToken(String userId, String token, String expiresAt) throws SQLException {
        String sql = "INSERT INTO auto_login_tokens (userId, token, expires_at) VALUES (?, ?, ?)";
        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[] { userId, token, expiresAt })) {
            dbUtil.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("토큰 저장 중 오류 발생: " + e.getMessage());
        }
    }

    // 토큰으로 사용자 ID를 조회하는 메서드
    public String getUserIdByToken(String token) throws SQLException {
        String sql = "SELECT userId FROM auto_login_tokens WHERE token = ? AND expires_at > ?";
        String now = DateUtil.localDateTimeToString(LocalDateTime.now());
        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[] { token, now })) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return rs.getString("userId");
            }
        } catch (SQLException e) {
            throw new SQLException("토큰으로 사용자 ID 조회 중 오류 발생: " + e.getMessage());
        }
        return null;
    }

    // 토큰을 삭제하는 메서드
    public void deleteToken(String token) throws SQLException {
        String sql = "DELETE FROM auto_login_tokens WHERE token = ?";
        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[] { token })) {
            dbUtil.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("토큰 삭제 중 오류 발생: " + e.getMessage());
        }
    }

    // 토큰의 만료 시간을 갱신하는 메서드
    public void refreshToken(String token) throws SQLException {
        String sql = "UPDATE auto_login_tokens SET expires_at = ? WHERE token = ?";
        String newExpiresAt = DateUtil.localDateTimeToString(LocalDateTime.now().plusDays(30)); // 30일 연장

        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[] { newExpiresAt, token })) {
            dbUtil.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("토큰 갱신 중 오류 발생: " + e.getMessage());
        }
    }

    // 만료된 토큰을 삭제하는 메서드
    public void deleteExpiredTokens() throws SQLException {
        String sql = "DELETE FROM auto_login_tokens WHERE expires_at <= ?";
        String now = DateUtil.localDateTimeToString(LocalDateTime.now());
        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[] { now })) {
            dbUtil.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("만료된 토큰 삭제 중 오류 발생: " + e.getMessage());
        }
    }

    // 사용자 ID로 자동 로그인 토큰을 삭제하는 메서드
    public void deleteTokenByUserId(String userId) throws SQLException {
        String sql = "DELETE FROM tbl_auto_login WHERE userId = ?";
        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new String[] { userId })) {
            dbUtil.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("자동 로그인 토큰 삭제 중 오류 발생: " + e.getMessage());
        }
    }
}
