package net.haebup.dao.lecture;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;
import net.haebup.dto.lecture.LectureDTO;
import net.haebup.dto.member.payment.CartItemDTO;
import net.haebup.utils.DatabaseUtil.DBConnPool;
import net.haebup.utils.DatabaseUtil.DbQueryUtil;
import java.util.ArrayList;
import java.sql.ResultSet;
// import net.haebup.dto.lecture.lectureDetail.LectureDetailDTO;
import net.haebup.dto.lecture.lectureDetail.LectureDetailDTO;

// LectureDAO: 강의 관련 데이터베이스 작업을 처리하는 클래스
public class LectureDAO {

    // 강의 목록을 조회하는 메소드 (정렬 및 페이징 적용)
    public List<LectureDTO> getLectureList(String sortBy, String filterBy, String filterValue, int limit, int offset)
            throws SQLException {
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT l.lecture_code, l.lecture_name, l.lecture_price, l.lecture_regdate, ");
        sql.append("l.lecture_limit_date, l.teacher_id, m.user_name AS teacher_name ");
        sql.append("FROM tbl_lecture l ");
        sql.append("JOIN tbl_member m ON l.teacher_id = m.user_id ");

        List<Object> params = new ArrayList<>();

        // 필터링 조건 추가
        if (filterBy != null && filterValue != null) {
            switch (filterBy) {
                case "subject":
                    sql.append("WHERE l.lecture_code LIKE ? ");
                    params.add(filterValue + "%");
                    break;
                case "teacher":
                    sql.append("WHERE m.user_name LIKE ?");
                    params.add("%" + filterValue + "%");
                    break;
                case "title":
                    sql.append("WHERE l.lecture_name LIKE ?");
                    params.add("%" + filterValue + "%");
                    break;
            }
        }

        // 정렬 조건 추가
        if (sortBy != null) {
            sql.append("ORDER BY ");
            switch (sortBy) {
                case "price_asc":
                    sql.append("l.lecture_price ASC ");
                    break;
                case "price_desc":
                    sql.append("l.lecture_price DESC ");
                    break;
                case "date_asc":
                    sql.append("l.lecture_regdate ASC ");
                    break;
                case "date_desc":
                    sql.append("l.lecture_regdate DESC ");
                    break;
                default:
                    sql.append("l.lecture_regdate DESC ");
            }
        } else {
            sql.append("ORDER BY l.lecture_regdate DESC ");
        }

        // 페이징 처리
        sql.append("LIMIT ? OFFSET ?");
        params.add(limit);
        params.add(offset);

        List<LectureDTO> lectureList = new ArrayList<>();

        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql.toString(), params.toArray())) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                LectureDTO lectureDTO = new LectureDTO();
                lectureDTO.setLectureCode(rs.getString("lecture_code"));
                lectureDTO.setLectureName(rs.getString("lecture_name"));
                lectureDTO.setLecturePrice(rs.getInt("lecture_price"));
                lectureDTO.setLectureRegdate(rs.getString("lecture_regdate"));
                lectureDTO.setLectureLimitDate(rs.getString("lecture_limit_date"));
                lectureDTO.setTeacherId(rs.getString("teacher_id"));
                lectureDTO.setTeacherName(rs.getString("teacher_name"));
                lectureList.add(lectureDTO);
            }
        }

        return lectureList;
    }

    // 강의 코드로 상세 정보를 조회하는 메소드
    public LectureDTO getLectureDetailByCode(String lectureCode) throws SQLException {
        String sql = "SELECT l.*, m.user_name AS teacher_name FROM tbl_lecture l " +
                "JOIN tbl_member m ON l.teacher_id = m.user_id " +
                "WHERE l.lecture_code = ?";
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { lectureCode })) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return null; // 이 부분은 실제로 LectureDTO 객체를 생성하여 반환해야 함
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("강의 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return null;
    }

    // 필터링을 적용한 강의 총 개수를 조회하는 메소드
    public int getLectureTotalCount(String filterBy, String filterValue) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM tbl_lecture ");
        List<Object> params = new ArrayList<>();

        // 필터링 조건 추가
        if (filterBy != null && filterValue != null) {
            switch (filterBy) {
                case "subject":
                    sql.append("WHERE lecture_code LIKE ? ");
                    params.add(filterValue + "%");
                    break;
                case "teacher":
                    sql.append("WHERE teacher_id = ? ");
                    params.add(filterValue);
                    break;
            }
        }

        // 데이터베이스 연결 및 쿼리 실행
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql.toString(), params.toArray())) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("강의 총 개수 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return 0;
    }

    // 강의 상세 정보를 조회하는 메소드
    public LectureDTO getLectureDetail(String lectureCode) throws SQLException {
        String sql = "SELECT l.*, m.user_name AS teacher_name FROM tbl_lecture l " +
                "JOIN tbl_member m ON l.teacher_id = m.user_id " +
                "WHERE l.lecture_code = ?";
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { lectureCode })) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                LectureDTO lectureDTO = new LectureDTO();
                lectureDTO.setLectureCode(rs.getString("lecture_code"));
                lectureDTO.setLectureName(rs.getString("lecture_name"));
                lectureDTO.setLecturePrice(rs.getInt("lecture_price"));
                lectureDTO.setLectureRegdate(rs.getString("lecture_regdate"));
                lectureDTO.setLectureLimitDate(rs.getString("lecture_limit_date"));
                lectureDTO.setTeacherId(rs.getString("teacher_id"));
                lectureDTO.setTeacherName(rs.getString("teacher_name"));
                return lectureDTO;
            }
            return null;
        }
    }
    //강의 상세정보 조회
    public LectureDetailDTO getLectureDetailByIdx(String lectureCode, int detailIdx) throws SQLException {
        String sql = "SELECT * FROM tbl_lecture_detail WHERE lecture_code = ? AND lecture_detail_idx = ?";
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { lectureCode, detailIdx })) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                LectureDetailDTO detail = new LectureDetailDTO();
                detail.setLectureDetailIdx(rs.getInt("lecture_detail_idx"));
                detail.setLectureCode(rs.getString("lecture_code"));
                detail.setLectureDetailContent(rs.getString("lecture_detail_content"));
                detail.setLectureDetailFilePath(rs.getString("lecture_detail_file_path"));
                detail.setLectureDetailFileName(rs.getString("lecture_detail_file_name"));
                detail.setLectureDetailFileSize(rs.getLong("lecture_detail_file_size"));
                return detail;
            }
            return null;
        }
    }

    // 새로운 강의를 등록하는 메소드
    public int insertLecture(LectureDTO lectureDTO) throws SQLException {
        String sql = "INSERT INTO tbl_lecture (lecture_code, lecture_name, lecture_price, lecture_limit_date, teacher_id) "
                +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] {
                        lectureDTO.getLectureCode(),
                        lectureDTO.getLectureName(),
                        lectureDTO.getLecturePrice(),
                        lectureDTO.getLectureLimitDate(),
                        lectureDTO.getTeacherId()
                })) {
            return dbUtil.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("새 강의 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
    // //강의 상세정보 등록
    // public int insertLectureDetail(LectureDetailDTO lectureDetailDTO) throws SQLException {
    //     String sql = "INSERT INTO tbl_lecture_detail (lecture_code, lecture_detail_content, lecture_detail_file_path, lecture_detail_file_name, lecture_detail_file_size) VALUES (?, ?, ?, ?, ?)";
        
    //     try (Connection conn = DBConnPool.getConnection();
    //          DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] {
    //              lectureDetailDTO.getLectureCode(),
    //              lectureDetailDTO.getLectureDetailContent(),
    //              lectureDetailDTO.getLectureDetailFilePath(),
    //              lectureDetailDTO.getLectureDetailFileName(),
    //              lectureDetailDTO.getLectureDetailFileSize()
    //          })) {
    //         return dbUtil.executeUpdate();
    //     } catch (SQLException e) {
    //         e.printStackTrace();
    //         throw new SQLException("강의 상세정보 등록 중 오류가 발생했습니다: " + e.getMessage());
    //     }
    // }

    // 강의 정보를 수정하는 메소드
    public int updateLecture(LectureDTO lectureDTO) throws SQLException {
        String sql = "UPDATE tbl_lecture SET lecture_name = ?, lecture_price = ?, lecture_limit_date = ? " +
                "WHERE lecture_code = ?";

        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] {
                        lectureDTO.getLectureName(),
                        lectureDTO.getLecturePrice(),
                        lectureDTO.getLectureLimitDate(),
                        lectureDTO.getLectureCode()
                })) {
            return dbUtil.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("강의 정보 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 강의를 삭제하는 메소드
    public int deleteLecture(String lectureCode) throws SQLException {
        String sql = "DELETE FROM tbl_lecture WHERE lecture_code = ?";

        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { lectureCode })) {
            return dbUtil.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("강의 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 선생님 아이디로 강의 목록 조회

    public List<LectureDTO> getLectureListByTeacherId(String teacherId) throws SQLException {
        String sql = "SELECT lecture_code, lecture_name, lecture_price ,lecture_regdate FROM tbl_lecture WHERE teacher_id = ?";
        List<LectureDTO> lectureList = new ArrayList<>();
        try (Connection conn = DBConnPool.getConnection();

                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { teacherId })) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                LectureDTO lectureDTO = new LectureDTO();
                lectureDTO.setLectureCode(rs.getString("lecture_code"));
                lectureDTO.setLectureName(rs.getString("lecture_name"));
                lectureDTO.setLecturePrice(rs.getInt("lecture_price"));
                lectureDTO.setLectureRegdate(rs.getString("lecture_regdate"));
                lectureList.add(lectureDTO);
            }
        }
        return lectureList;
    }

    // 유저 아이디로 결제한 강의 목록 조회
    public List<LectureDTO> getLectureListByUserId(String userId, boolean limit) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT p.lecture_start_date, p.lecture_code, l.lecture_name, m.user_name AS teacher_name ");
        sql.append("FROM tbl_payment p ");
        sql.append("INNER JOIN tbl_lecture l ON p.lecture_code = l.lecture_code ");
        sql.append("INNER JOIN tbl_member m ON l.teacher_id = m.user_id ");
        sql.append("WHERE p.user_id = ? AND p.payment_status = 'P'");
        if (limit) {
            sql.append(" LIMIT 4");
        }

        List<LectureDTO> lectureList = new ArrayList<>();

        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql.toString(), new Object[] { userId })) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                LectureDTO lectureDTO = new LectureDTO();
                String lectureStartDate = rs.getString("lecture_start_date");
                lectureDTO.setLectureStartDate(lectureStartDate != null ? lectureStartDate : "시작 전");
                lectureDTO.setLectureCode(rs.getString("lecture_code"));
                lectureDTO.setLectureName(rs.getString("lecture_name"));
                lectureDTO.setTeacherName(rs.getString("teacher_name"));
                lectureList.add(lectureDTO);
            }
        }
        return lectureList;
    }
    
    //유저아이디로 결제한 강의 총 개수 조회
    public int getLectureTotalCountByUserId(String userId) throws SQLException {
            String sql = "SELECT COUNT(*) AS total FROM tbl_payment WHERE user_id = ? AND payment_status = 'P'";
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId })) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
            return 0; // 결과가 없을 경우 0 반환
        }
    }

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

    public List<LectureDTO> getLecturesByTeacherId(String teacherId) throws SQLException {
        String sql = "SELECT l.*, m.user_name AS teacher_name " +
                    "FROM tbl_lecture l " +
                    "JOIN tbl_member m ON l.teacher_id = m.user_id " +
                    "WHERE l.teacher_id = ? " +
                    "ORDER BY l.lecture_regdate DESC";

        List<LectureDTO> lectureList = new ArrayList<>();
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { teacherId })) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                LectureDTO lectureDTO = new LectureDTO();
                lectureDTO.setLectureCode(rs.getString("lecture_code"));
                lectureDTO.setLectureName(rs.getString("lecture_name"));
                lectureDTO.setLecturePrice(rs.getInt("lecture_price"));
                lectureDTO.setLectureRegdate(rs.getString("lecture_regdate"));
                lectureDTO.setLectureLimitDate(rs.getString("lecture_limit_date"));
                lectureDTO.setTeacherId(rs.getString("teacher_id"));
                lectureDTO.setTeacherName(rs.getString("teacher_name"));
                lectureList.add(lectureDTO);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("선생님의 강의 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return lectureList;
    }
}
