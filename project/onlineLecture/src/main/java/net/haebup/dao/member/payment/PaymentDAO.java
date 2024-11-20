package net.haebup.dao.member.payment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.haebup.dto.member.payment.CartItemDTO;
import net.haebup.dto.member.payment.PaymentDTO;
import net.haebup.utils.DatabaseUtil.DBConnPool;
import net.haebup.utils.DatabaseUtil.DbQueryUtil;


public class PaymentDAO {

    // 결제내역 조회
    public List<PaymentDTO> getPaymentList(int limit, int offset, String userId) throws SQLException {
        String sql = "SELECT * FROM tbl_payment WHERE user_id = ? ORDER BY payment_idx DESC LIMIT = ? OFFSET = ?";
        List<PaymentDTO> paymentList = new ArrayList<PaymentDTO>();
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, limit, offset })) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                PaymentDTO paymentDTO = new PaymentDTO();
                paymentDTO.setPaymentIdx(rs.getInt("payment_idx"));
                paymentDTO.setUserId(rs.getString("user_id"));
                paymentDTO.setLectureCode(rs.getString("lecture_code"));
                paymentDTO.setPaymentDate(rs.getString("payment_date"));
                paymentDTO.setPaymentStatus(rs.getString("payment_status"));
                paymentDTO.setLectureStartDate(rs.getString("lecture_start_date"));
                paymentList.add(paymentDTO);
            }
            return paymentList;
        }
    }
    //결제내역 전부 조회
    public List<PaymentDTO> getPaymentListAll(String userId) throws SQLException {
        String sql = "SELECT p.*, l.lecture_name, l.lecture_price FROM tbl_payment p " +
                    "INNER JOIN tbl_lecture l ON p.lecture_code = l.lecture_code " +
                    "WHERE p.user_id = ? AND p.payment_status != 'I'";
        List<PaymentDTO> paymentList = new ArrayList<PaymentDTO>();
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId })) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                PaymentDTO paymentDTO = new PaymentDTO();
                paymentDTO.setPaymentIdx(rs.getInt("payment_idx"));
                paymentDTO.setUserId(rs.getString("user_id")); 
                paymentDTO.setLectureCode(rs.getString("lecture_code"));
                paymentDTO.setPaymentDate(rs.getString("payment_date"));
                paymentDTO.setPaymentStatus(rs.getString("payment_status"));
                paymentDTO.setLectureName(rs.getString("lecture_name"));
                paymentDTO.setLecturePrice(rs.getInt("lecture_price"));
                paymentList.add(paymentDTO);
            }
        }
        return paymentList;
    }

    // 결제내역 총 개수 조회
    public int getPaymentCount(String userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tbl_payment WHERE user_id = ?";
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId })) {
            ResultSet rs = dbUtil.executeQuery();
            return rs.getInt(1);
        }
    }

    //단일 결제상태 업데이트
    public int updatePaymentStatus(int paymentIdx, String paymentStatus) throws SQLException {
        String sql = "UPDATE tbl_payment SET payment_status = ? WHERE payment_idx = ?";
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { paymentStatus, paymentIdx })) {
            return dbUtil.executeUpdate();
        }
    }

    // 장바구니 추가
    public int addCart(String userId, String lectureCode) throws SQLException {
        String sql = "INSERT INTO tbl_payment (user_id, lecture_code, payment_status) VALUES (?, ?, 'I')";
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, lectureCode })) {
            return dbUtil.executeUpdate();
        }
    }

    // 장바구니 조회
    public List<CartItemDTO> getCartListWithDetails(String userId) throws SQLException {
        String sql = "SELECT p.payment_idx, p.lecture_code, l.lecture_name, l.lecture_price, m.user_name as teacher_name " +
                "FROM tbl_payment p " +
                "INNER JOIN tbl_lecture l ON p.lecture_code = l.lecture_code " +
                "INNER JOIN tbl_member m ON l.teacher_id = m.user_id " +
                "WHERE p.user_id = ? AND p.payment_status = 'I'";

        List<CartItemDTO> cartList = new ArrayList<>();

        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId })) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                CartItemDTO cartItem = new CartItemDTO();
                cartItem.setPaymentIdx(rs.getInt("payment_idx"));
                cartItem.setLectureCode(rs.getString("lecture_code"));
                cartItem.setLectureName(rs.getString("lecture_name"));
                cartItem.setLecturePrice(rs.getInt("lecture_price"));
                cartItem.setTeacherName(rs.getString("teacher_name"));
                cartList.add(cartItem);
            }
        }
        return cartList;
    }

    // 장바구니 중복 체크
    public boolean isLectureInCart(String userId, String lectureCode) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tbl_payment WHERE user_id = ? AND lecture_code = ? AND payment_status = 'I'";
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, lectureCode })) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    // 여러 강의 장바구니 추가
    public int addMultipleToCart(String userId, List<String> lectureCodes) throws SQLException {
        String sql = "INSERT IGNORE INTO tbl_payment (user_id, lecture_code, payment_status) " +
                "VALUES (?, ?, 'I')";

        int successCount = 0;

        try (Connection conn = DBConnPool.getConnection()) {
            for (String lectureCode : lectureCodes) {
                try (DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, lectureCode })) {
                    int result = dbUtil.executeUpdate();
                    if (result > 0)
                        successCount++;
                }
            }
        }

        return successCount;
    }

    // 선택한 강의 결제
    public int paySelectedLectures(String userId, List<String> lectureCodes) throws SQLException {
        if (lectureCodes == null || lectureCodes.isEmpty()) {
            throw new IllegalArgumentException("강의 코드 리스트가 비어있습니다.");
        }

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("UPDATE tbl_payment SET payment_date = CURRENT_TIMESTAMP, payment_status = 'P' ")
                .append("WHERE user_id = ? AND lecture_code IN (");

        for (int i = 0; i < lectureCodes.size(); i++) {
            if (i > 0) {
                sqlBuilder.append(", ");
            }
            sqlBuilder.append("?");
        }

        sqlBuilder.append(") AND payment_status = 'I'");

        String sql = sqlBuilder.toString();

        try (Connection conn = DBConnPool.getConnection()) {
            Object[] params = new Object[lectureCodes.size() + 1];
            params[0] = userId;
            for (int i = 0; i < lectureCodes.size(); i++) {
                params[i + 1] = lectureCodes.get(i);
            }

            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, params)) {
                return dbUtil.executeUpdate();
            }
        }
    }
    //총 결제 금액 계산 //안써도 됨
    public int calculateTotalAmount(String userId, List<String> lectureCodes) throws SQLException {
        StringBuilder sql = new StringBuilder(
            "SELECT SUM(l.lecture_price) FROM tbl_payment p " +
            "INNER JOIN tbl_lecture l ON p.lecture_code = l.lecture_code " +
            "WHERE p.user_id = ? AND p.lecture_code IN ("
        );
        for (int i = 0; i < lectureCodes.size(); i++) {
            sql.append(i == 0 ? "?" : ", ?");
        }
        sql.append(") AND p.payment_status = 'I'");

        try (Connection conn = DBConnPool.getConnection()) {
            Object[] params = new Object[lectureCodes.size() + 1];
            params[0] = userId;
            for (int i = 0; i < lectureCodes.size(); i++) {
                params[i + 1] = lectureCodes.get(i);
            }

            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, sql.toString(), params)) {
                ResultSet rs = dbUtil.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }
    //구매여부 조회
    public boolean isPaid(String userId, String lectureCode) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM tbl_payment WHERE user_id = ? AND lecture_code = ? AND payment_status = 'P'";
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, lectureCode })) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") > 0;
            }
            return false;
        }
    }
    //강의 시작일 업데이트
    public int updateLectureStartDate(String userId, String lectureCode) throws SQLException {
        String sql = "UPDATE tbl_payment SET lecture_start_date = NOW() WHERE user_id = ? AND lecture_code = ?";
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, lectureCode })) {
            return dbUtil.executeUpdate();
        }
    }
    //강의 환불(시작일 없을떄)
    public int repaymentLecture(String userId, String lectureCode) throws SQLException {
        String sql = "UPDATE tbl_payment SET payment_status = 'R' WHERE user_id = ? AND lecture_code = ? AND lecture_start_date IS NULL";
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, lectureCode })) {
            return dbUtil.executeUpdate();
        }
    }

    // PaymentDAO에 새로운 메서드 추가
    public List<PaymentDTO> getStudentsByLectureCode(String lectureCode) throws SQLException {
        String sql = "SELECT p.*, m.user_name, m.user_nickname, m.user_email " +
                "FROM tbl_payment p " +
                "JOIN tbl_member m ON p.user_id = m.user_id " +
                "WHERE p.lecture_code = ? " +
                "AND p.payment_status = 'P' " +
                "ORDER BY p.payment_date DESC";
                
        List<PaymentDTO> enrollments = new ArrayList<>();
        
        try (Connection conn = DBConnPool.getConnection();
             DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { lectureCode })) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                PaymentDTO payment = new PaymentDTO();
                payment.setPaymentIdx(rs.getInt("payment_idx"));
                payment.setUserId(rs.getString("user_id"));
                payment.setLectureCode(rs.getString("lecture_code"));
                payment.setPaymentDate(rs.getString("payment_date"));
                payment.setPaymentStatus(rs.getString("payment_status"));
                payment.setLectureStartDate(rs.getString("lecture_start_date"));
                
                // MemberDTO 정보도 추가
                payment.setUserName(rs.getString("user_name"));
                payment.setUserNickname(rs.getString("user_nickname"));
                payment.setUserEmail(rs.getString("user_email"));
                
                enrollments.add(payment);
            }
        }
        return enrollments;
    }

    // 장바구니 항목 삭제
    public int deleteCartItem(int paymentIdx) throws SQLException {
        String sql = "DELETE FROM tbl_payment WHERE payment_idx = ? AND payment_status = 'I'";
        try (Connection conn = DBConnPool.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { paymentIdx })) {
            return dbUtil.executeUpdate();
        }
    }
}
