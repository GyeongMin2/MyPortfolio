package dao;

import dto.inquiry.InquiryDTO;
import DbConnection.DBConnectionManager;
import DbConnection.DbQueryUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InquiryDAO {

    // 새로운 문의를 데이터베이스에 등록하는 메서드
    public void insertInquiry(InquiryDTO inquiry) throws SQLException {
        String sql = "INSERT INTO tbl_inquiry (userId, title, content, submit_date, category_main, category_sub, status) " +
                "VALUES (?, ?, ?, NOW(), ?, ?, 0)";

        try (Connection conn = DBConnectionManager.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{
                     inquiry.getUserId(),
                     inquiry.getTitle(),
                     inquiry.getContent(),
                     inquiry.getCategoryMain(),
                     inquiry.getCategorySub()
             })) {
            dbUtil.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("문의 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 특정 ID의 문의를 조회하는 메서드
    public InquiryDTO getInquiryById(int inquiryId) throws SQLException {
        String sql = "SELECT * FROM tbl_inquiry WHERE inquiry_id = ?";
        InquiryDTO inquiry = null;

        try (Connection conn = DBConnectionManager.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{inquiryId})) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                inquiry = new InquiryDTO();
                inquiry.setInquiryId(rs.getInt("inquiry_id"));
                inquiry.setUserId(rs.getString("userId"));
                inquiry.setTitle(rs.getString("title"));
                inquiry.setContent(rs.getString("content"));
                inquiry.setSubmitDate(rs.getString("submit_date"));
                inquiry.setCategoryMain(rs.getString("category_main"));
                inquiry.setCategorySub(rs.getString("category_sub"));
                inquiry.setStatus(rs.getInt("status"));
                inquiry.setResponse(rs.getString("response"));
                inquiry.setResponseDate(rs.getString("response_date"));
            }
        } catch (SQLException e) {
            throw new SQLException("문의 조회 중 오류가 발생했습니다: " + e.getMessage());
        }

        return inquiry;
    }

    // 특정 사용자의 문의를 페이징하여 조회하는 메서드
    public List<InquiryDTO> getInquiriesByUserId(String userId, int limit, int offset) throws SQLException {
        
        String sql = "SELECT * FROM tbl_inquiry WHERE userId = ? ORDER BY submit_date DESC LIMIT ? OFFSET ?";
        List<InquiryDTO> inquiries = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{userId, limit, offset})) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                InquiryDTO inquiry = new InquiryDTO();
                inquiry.setInquiryId(rs.getInt("inquiry_id"));
                inquiry.setUserId(rs.getString("userId"));
                inquiry.setTitle(rs.getString("title"));
                inquiry.setContent(rs.getString("content"));
                inquiry.setSubmitDate(rs.getString("submit_date"));
                inquiry.setCategoryMain(rs.getString("category_main"));
                inquiry.setCategorySub(rs.getString("category_sub"));
                inquiry.setStatus(rs.getInt("status"));
                inquiry.setResponse(rs.getString("response"));
                inquiry.setResponseDate(rs.getString("response_date"));
                inquiries.add(inquiry);
            }
        } catch (SQLException e) {
            throw new SQLException("사용자의 문의 페이징 조회 중 오류가 발생했습니다: " + e.getMessage());
        }

        return inquiries;
    }

    // 기존 문의를 수정하는 메서드
    public void updateInquiry(InquiryDTO inquiry) throws SQLException {
        String sql = "UPDATE tbl_inquiry SET title = ?, content = ?, category_main = ?, category_sub = ? WHERE inquiry_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{
                     inquiry.getTitle(),
                     inquiry.getContent(),
                     inquiry.getCategoryMain(),
                     inquiry.getCategorySub(),
                     inquiry.getInquiryId()
             })) {
            int affectedRows = dbUtil.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("문의 수정 중 오류가 발생했습니다.");
            }
        } catch (SQLException e) {
            throw new SQLException("문의 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    
    // 특정 ID의 문의를 삭제하는 메서드
    public void deleteInquiry(int inquiryId) throws SQLException {
        String sql = "DELETE FROM tbl_inquiry WHERE inquiry_id = ?";

        try (Connection conn = DBConnectionManager.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{inquiryId})) {
            dbUtil.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("문의 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
