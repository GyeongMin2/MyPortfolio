package net.haebup.dao.lecture;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import net.haebup.dto.lecture.ReportCardDTO;
import net.haebup.utils.DatabaseUtil.DBConnPool;
import net.haebup.utils.DatabaseUtil.DbQueryUtil;

public class ReportCardDAO {
    
    // 성적표 목록 조회
    public List<ReportCardDTO> getReportCards(String lectureCode, String userId) throws SQLException {
        String sql = "SELECT r.*, m.user_name, l.lecture_name " +
                    "FROM tbl_report_card r " +
                    "JOIN tbl_member m ON r.user_id = m.user_id " +
                    "JOIN tbl_lecture l ON r.lecture_code = l.lecture_code " +
                    "WHERE r.lecture_code = ? AND r.user_id = ? " +
                    "ORDER BY r.report_card_regdate DESC";
                    
        List<ReportCardDTO> reportCards = new ArrayList<>();
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { lectureCode, userId })) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                ReportCardDTO report = new ReportCardDTO();
                report.setReportCardIdx(rs.getInt("report_card_idx"));
                report.setLectureCode(rs.getString("lecture_code"));
                report.setReportCardName(rs.getString("report_card_name"));
                report.setReportCardRegdate(rs.getString("report_card_regdate"));
                report.setUserId(rs.getString("user_id"));
                report.setScore(rs.getString("score"));
                report.setUserName(rs.getString("user_name"));
                report.setLectureName(rs.getString("lecture_name"));
                reportCards.add(report);
            }
        }
        return reportCards;
    }
    
    // 성적표 추가
    public int insertReportCard(ReportCardDTO reportCard) throws SQLException {
        String sql = "INSERT INTO tbl_report_card (lecture_code, report_card_name, user_id, score) " +
                    "VALUES (?, ?, ?, ?)";
                    
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, 
                new Object[] { 
                    reportCard.getLectureCode(),
                    reportCard.getReportCardName(),
                    reportCard.getUserId(),
                    reportCard.getScore()
                })) {
            return dbUtil.executeUpdate();
        }
    }
    
    // 성적표 수정
    public int updateReportCard(ReportCardDTO reportCard) throws SQLException {
        String sql = "UPDATE tbl_report_card SET report_card_name = ?, score = ? " +
                    "WHERE report_card_idx = ?";
                    
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, 
                new Object[] { 
                    reportCard.getReportCardName(),
                    reportCard.getScore(),
                    reportCard.getReportCardIdx()
                })) {
            return dbUtil.executeUpdate();
        }
    }
    
    // 성적표 삭제
    public int deleteReportCard(int reportCardIdx) throws SQLException {
        String sql = "DELETE FROM tbl_report_card WHERE report_card_idx = ?";
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { reportCardIdx })) {
            return dbUtil.executeUpdate();
        }
    }
}
