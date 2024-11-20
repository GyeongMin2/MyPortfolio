package net.haebup.dao.lecture;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.ResultSet;
import net.haebup.dto.lecture.LectureDTO;
import net.haebup.dto.lecture.lectureDetail.LectureDetailDTO;
import net.haebup.utils.DatabaseUtil.DBConnPool;
import net.haebup.utils.DatabaseUtil.DbQueryUtil;

public class LectureAdminDAO {

    // 강의 추가 메소드
    public int insertLecture(LectureDTO lectureDTO) throws SQLException {
        String sql = "INSERT INTO tbl_lecture (lecture_code, lecture_name, lecture_price, lecture_limit_date, teacher_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] {
                 lectureDTO.getLectureCode(),
                 lectureDTO.getLectureName(),
                 lectureDTO.getLecturePrice(),
                 lectureDTO.getLectureLimitDate(),
                 lectureDTO.getTeacherId()
             })) {
            return dbUtil.executeUpdate();
        }
    }

    // 강의 수정 메소드
    public int updateLecture(LectureDTO lectureDTO) throws SQLException {
        String sql = "UPDATE tbl_lecture SET lecture_name = ?, lecture_price = ?, lecture_limit_date = ?, teacher_id = ? WHERE lecture_code = ?";
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] {
                 lectureDTO.getLectureName(),
                 lectureDTO.getLecturePrice(),
                 lectureDTO.getLectureLimitDate(),
                 lectureDTO.getTeacherId(),
                 lectureDTO.getLectureCode()
             })) {
            return dbUtil.executeUpdate();
        }
    }

    // 강의 삭제 메소드
    public int deleteLecture(String lectureCode) throws SQLException {
        String sql = "DELETE FROM tbl_lecture WHERE lecture_code = ?";
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { lectureCode })) {
            return dbUtil.executeUpdate();
        }
    }

    // 강의 상세 정보 조회 메소드
    public LectureDTO getLectureDetail(String lectureCode) throws SQLException {
        String sql = "SELECT * FROM tbl_lecture WHERE lecture_code = ?";
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { lectureCode })) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                LectureDTO lectureDTO = new LectureDTO();
                lectureDTO.setLectureCode(rs.getString("lecture_code"));
                lectureDTO.setLectureName(rs.getString("lecture_name"));
                lectureDTO.setLecturePrice(rs.getInt("lecture_price"));
                lectureDTO.setLectureLimitDate(rs.getString("lecture_limit_date"));
                lectureDTO.setTeacherId(rs.getString("teacher_id"));
                return lectureDTO;
            }
        }
        return null;
    }

    // 모든 강의 조회 메소드
    public List<LectureDTO> getAllLectures() throws SQLException {
        List<LectureDTO> lectures = new ArrayList<>();
        String sql = "SELECT * FROM tbl_lecture";
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] {})) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                LectureDTO lectureDTO = new LectureDTO();
                lectureDTO.setLectureCode(rs.getString("lecture_code"));
                lectureDTO.setLectureName(rs.getString("lecture_name"));
                lectureDTO.setLecturePrice(rs.getInt("lecture_price"));
                lectureDTO.setLectureLimitDate(rs.getString("lecture_limit_date"));
                lectureDTO.setTeacherId(rs.getString("teacher_id"));
                lectures.add(lectureDTO);
            }
        }
        return lectures;
    }

    // 강의 상세 내용 조회
    public List<LectureDetailDTO> getLectureDetails(String lectureCode) throws SQLException {
        String sql = "SELECT * FROM tbl_lecture_detail WHERE lecture_code = ? ORDER BY lecture_detail_idx";
        List<LectureDetailDTO> details = new ArrayList<>();
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{lectureCode})) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                LectureDetailDTO detail = new LectureDetailDTO();
                detail.setLectureDetailIdx(rs.getInt("lecture_detail_idx"));
                detail.setLectureCode(rs.getString("lecture_code"));
                detail.setLectureDetailContent(rs.getString("lecture_detail_content"));
                detail.setLectureDetailFilePath(rs.getString("lecture_detail_file_path"));
                detail.setLectureDetailFileName(rs.getString("lecture_detail_file_name"));
                detail.setLectureDetailFileSize(rs.getLong("lecture_detail_file_size"));
                details.add(detail);
            }
        }
        return details;
    }

    // 강의 상세 내용 추가
    public int insertLectureDetail(LectureDetailDTO detail) throws SQLException {
        String sql = "INSERT INTO tbl_lecture_detail (lecture_code, lecture_detail_content, lecture_detail_file_path, lecture_detail_file_name, lecture_detail_file_size) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{
                 detail.getLectureCode(),
                 detail.getLectureDetailContent(),
                 detail.getLectureDetailFilePath(),
                 detail.getLectureDetailFileName(),
                 detail.getLectureDetailFileSize()
             })) {
            return dbUtil.executeUpdate();
        }
    }

    // 강의 상세 내용 수정
    public int updateLectureDetail(LectureDetailDTO detail) throws SQLException {
        String sql = "UPDATE tbl_lecture_detail SET lecture_detail_content = ?, lecture_detail_file_path = ?, lecture_detail_file_name = ?, lecture_detail_file_size = ? WHERE lecture_detail_idx = ? AND lecture_code = ?";
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{
                 detail.getLectureDetailContent(),
                 detail.getLectureDetailFilePath(),
                 detail.getLectureDetailFileName(),
                 detail.getLectureDetailFileSize(),
                 detail.getLectureDetailIdx(),
                 detail.getLectureCode()
             })) {
            return dbUtil.executeUpdate();
        }
    }

    // 강의 상세 내용 삭제
    public int deleteLectureDetail(int detailIdx, String lectureCode) throws SQLException {
        String sql = "DELETE FROM tbl_lecture_detail WHERE lecture_detail_idx = ? AND lecture_code = ?";
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[]{detailIdx, lectureCode})) {
            return dbUtil.executeUpdate();
        }
    }
}
