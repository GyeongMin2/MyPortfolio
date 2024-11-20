package dao;

import dto.message.MessageContentDTO;
import dto.message.MessageFileDTO;
import dto.message.MessageStatusDTO;
import dto.message.MessageDTO;
import DbConnection.DBConnectionManager;
import DbConnection.DbQueryUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MessageContentDAO {

    // 메시지 등록 (단일 첨부파일 포함)
    // 메시지 내용, 상태, 첨부 파일을 각각의 테이블에 삽입
    public void insertMessageContent(MessageContentDTO messageContent, MessageFileDTO messageFile,
            MessageStatusDTO senderStatus, MessageStatusDTO receiverStatus) throws SQLException {
        String sql = "INSERT INTO tbl_message_content (content, send_date, message_title) VALUES (?, NOW(), ?)";
        String statusSql = "INSERT INTO tbl_message_status (message_id, userId, is_sender, read_status, delete_status) VALUES (?, ?, ?, ?, ?)";
        String fileSql = "INSERT INTO tbl_message_file (message_id, file_name, file_path, file_size, file_date) VALUES (?, ?, ?, ?, NOW())";

        try (Connection conn = DBConnectionManager.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int messageId = 0;
                // 메시지 내용 삽입
                try (DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] {
                        messageContent.getContent(),
                        messageContent.getMessageTitle()
                })) {
                    dbUtil.executeUpdate();
                    // 생성된 messageId 가져오기
                    try (ResultSet generatedKeys = dbUtil.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            messageId = generatedKeys.getInt(1);
                        } else {
                            throw new SQLException("메시지 ID를 가져오는데 실패했습니다.");
                        }
                    }
                }

                // 발신자 상태 삽입
                try (DbQueryUtil dbUtil = new DbQueryUtil(conn, statusSql, new Object[] {
                        messageId,
                        senderStatus.getUserId(),
                        senderStatus.getIsSender(),
                        senderStatus.getReadStatus(),
                        senderStatus.getDeleteStatus()
                })) {
                    dbUtil.executeUpdate();
                }

                // 수신자 상태 삽입
                try (DbQueryUtil dbUtil = new DbQueryUtil(conn, statusSql, new Object[] {
                        messageId,
                        receiverStatus.getUserId(),
                        receiverStatus.getIsSender(),
                        receiverStatus.getReadStatus(),
                        receiverStatus.getDeleteStatus()
                })) {
                    dbUtil.executeUpdate();
                }

                // 첨부 파일이 있는 경우에만 파일 정보 삽입
                if (messageFile != null) {
                    try (DbQueryUtil dbUtil = new DbQueryUtil(conn, fileSql, new Object[] {
                            messageId,
                            messageFile.getFileName(),
                            messageFile.getFilePath(),
                            messageFile.getFileSize()
                    })) {
                        dbUtil.executeUpdate();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new SQLException("메시지 등록 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 특정 message_id로 메시지 조 (첨부파일 포함)
    // 메시지 내용과 첨부 파일 정보를 조회하여 반환
    public MessageContentDTO getMessageContentById(int messageId) throws SQLException {
        String sql = "SELECT * FROM tbl_message_content WHERE message_id = ?";
        String fileSql = "SELECT * FROM tbl_message_file WHERE message_id = ?";
        MessageContentDTO messageContent = null;
        MessageFileDTO messageFile = null;

        try (Connection conn = DBConnectionManager.getConnection()) {
            // 메시지 내용 조회
            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { messageId })) {
                ResultSet rs = dbUtil.executeQuery();
                if (rs.next()) {
                    messageContent = new MessageContentDTO();
                    messageContent.setMessageId(rs.getInt("message_id"));
                    messageContent.setContent(rs.getString("content"));
                    messageContent.setSendDate(rs.getString("send_date"));
                    messageContent.setReadDate(rs.getString("read_date"));
                    messageContent.setMessageTitle(rs.getString("message_title"));
                }
            }

            // 첨부파일 조회 (단일 파일)
            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, fileSql, new Object[] { messageId })) {
                ResultSet rs = dbUtil.executeQuery();
                if (rs.next()) {
                    messageFile = new MessageFileDTO();
                    messageFile.setMessageId(rs.getInt("message_id"));
                    messageFile.setFileId(rs.getInt("file_id"));
                    messageFile.setFileName(rs.getString("file_name"));
                    messageFile.setFilePath(rs.getString("file_path"));
                    messageFile.setFileSize(rs.getLong("file_size"));
                    messageFile.setFileDate(rs.getString("file_date"));
                }
            }
            if (messageContent != null) {
                messageContent.setFile(messageFile);
            }

        } catch (SQLException e) {
            throw new SQLException("메시지 정보를 조회하는 중 오류가 발생했습니다: " + e.getMessage());
        }

        return messageContent;
    }

    // 특정 범위 메시지 조회 (첨부파일 제외, 상태 포함) - 페이징 처리
    // 지정된 범위의 메시지 목록을 조회하여 반환
    public List<MessageDTO> getMessagesByRange(int limit, int offset, String userId, int isSender) throws SQLException {
        String messageSql = "SELECT mc.message_id, mc.content, mc.send_date, mc.read_date, mc.message_title, CASE WHEN mf.file_id IS NOT NULL THEN 1 ELSE 0 END AS has_file_attachment FROM tbl_message_content mc "
                +
                "JOIN tbl_message_status ms ON mc.message_id = ms.message_id " +
                "LEFT JOIN tbl_message_file mf ON mc.message_id = mf.message_id " +
                "WHERE ms.userId = ? AND ms.is_sender = ? " +
                "ORDER BY mc.send_date DESC LIMIT ? OFFSET ?";
        List<MessageDTO> messages = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection()) {
            // 메시지 목록 조회
            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, messageSql,
                    new Object[] { userId, isSender, limit, offset })) {
                ResultSet rs = dbUtil.executeQuery();
                while (rs.next()) {
                    MessageDTO message = new MessageDTO();
                    message.setMessageId(rs.getInt("message_id"));
                    message.setContent(rs.getString("content"));
                    message.setSendDate(rs.getString("send_date"));
                    message.setReadDate(rs.getString("read_date"));
                    message.setMessageTitle(rs.getString("message_title"));
                    message.setHasFileAttachment(rs.getInt("has_file_attachment") == 1);
                    messages.add(message);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("메시지 목록을 조회하는 중 오류가 발생했습니다.");
        }

        return messages;
    }

    // 메시지 읽은 시간 업데이트
    // 메시지 내용과 상태 테이블의 읽은 시간을 현재 시간으로 업데이트
    public void updateReadDate(int messageId) throws SQLException {
        String updateReadDateSQL = "UPDATE tbl_message_content SET read_date = NOW() WHERE message_id = ?";
        String updateReadStatusSQL = "UPDATE tbl_message_status SET read_status = 1 WHERE message_id = ?";

        try (Connection conn = DBConnectionManager.getConnection()) {
            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, updateReadDateSQL, new Object[] { messageId })) {
                dbUtil.executeUpdate();
            }
            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, updateReadStatusSQL, new Object[] { messageId })) {
                dbUtil.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException("메시지 읽은 시간 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // CREATE PROCEDURE `TSPOON`.`DeleteMessageIfBothDeleted`(IN p_message_id INT)
    // BEGIN
    // DECLARE sender_deleted BOOLEAN;
    // DECLARE receiver_deleted BOOLEAN;

    // SELECT COUNT(*) > 0 INTO sender_deleted
    // FROM tbl_message_status
    // WHERE message_id = p_message_id AND is_sender = 1 AND delete_status = 1;

    // SELECT COUNT(*) > 0 INTO receiver_deleted
    // FROM tbl_message_status
    // WHERE message_id = p_message_id AND is_sender = 2 AND delete_status = 1;

    // IF sender_deleted AND receiver_deleted THEN
    // DELETE FROM tbl_message_content WHERE message_id = p_message_id;
    // DELETE FROM tbl_message_status WHERE message_id = p_message_id;
    // DELETE FROM tbl_message_file WHERE message_id = p_message_id;
    // SELECT 1;
    // ELSE
    // SELECT 0;
    // END IF;
    // END

    // 메시지 삭제 (논리 삭제 후 실제 삭제 여부 판단)
    // 메시지 상태를 삭제로 변경하고, 양쪽 모두 삭제한 경우 실제 삭제 수행
    public boolean deleteMessageContent(int messageId, int isSender) throws SQLException {
        String logicalDeleteSQL = "UPDATE tbl_message_status SET delete_status = 1 WHERE message_id = ? AND is_sender = ?";
        String deleteProcedureCall = "CALL DeleteMessageIfBothDeleted(?)";
        boolean isPhysicallyDeleted = false;

        try (Connection conn = DBConnectionManager.getConnection()) {
            // 논리 삭제
            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, logicalDeleteSQL, new Object[] { messageId, isSender })) {
                dbUtil.executeUpdate();
            }

            // 실제 삭제 여부 판단을 위한 프로시저 호출
            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, deleteProcedureCall, new Object[] { messageId })) {
                int affectedRows = dbUtil.executeUpdate();
                if (affectedRows > 0) {
                    isPhysicallyDeleted = true;
                }
            }

        } catch (SQLException e) {
            throw new SQLException("메시지 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }

        return isPhysicallyDeleted;
    }

    // 읽지 않은 쪽지 개수 조회
    // 특정 사용자의 읽지 않은 수신 메시지 개수를 반환
    public int getUnreadMessageCount(String userId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tbl_message_status WHERE userId = ? AND is_sender = 2 AND read_status = 0";
        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId })) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new SQLException("읽지 않은 쪽지 개수 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return 0;
    }

    // 정 사용자의 최근 쪽지 목록 조회 (발신/수신 구분)
    // 지정된 사용자의 최근 메시지 목록을 조회하여 반환
    public List<MessageDTO> getRecentMessages(String userId, int isSender, int limit) throws SQLException {
        String sql = "SELECT mc.*, ms.read_status, CASE WHEN mf.file_id IS NOT NULL THEN 1 ELSE 0 END AS has_file_attachment "
                +
                "FROM tbl_message_content mc " +
                "JOIN tbl_message_status ms ON mc.message_id = ms.message_id " +
                "LEFT JOIN tbl_message_file mf ON mc.message_id = mf.message_id " +
                "WHERE ms.userId = ? AND ms.is_sender = ? " +
                "ORDER BY mc.send_date DESC LIMIT ?";
        List<MessageDTO> messages = new ArrayList<>();
        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, isSender, limit })) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                MessageDTO message = new MessageDTO();
                message.setMessageId(rs.getInt("message_id"));
                message.setContent(rs.getString("content"));
                message.setSendDate(rs.getString("send_date"));
                message.setReadDate(rs.getString("read_date"));
                message.setMessageTitle(rs.getString("message_title"));
                message.setHasFileAttachment(rs.getBoolean("has_file_attachment"));

                MessageStatusDTO status = new MessageStatusDTO();
                status.setReadStatus(rs.getInt("read_status"));
                message.setStatus(status);

                messages.add(message);
            }
        } catch (SQLException e) {
            throw new SQLException("최근 쪽지 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return messages;
    }

    // 쪽지 상태 업데이트 (읽음 상태 변경)
    // 메시지의 읽음 상태를 변경하고 읽은 시간을 업데이트
    public void updateMessageReadStatus(int messageId, String userId) throws SQLException {
        String sql = "UPDATE tbl_message_status SET read_status = 1 WHERE message_id = ? AND userId = ? AND is_sender = 2";
        String updateContentSql = "UPDATE tbl_message_content SET read_date = NOW() WHERE message_id = ?";

        try (Connection conn = DBConnectionManager.getConnection()) {
            conn.setAutoCommit(false);
            try {
                try (DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { messageId, userId })) {
                    dbUtil.executeUpdate();
                }
                try (DbQueryUtil dbUtil = new DbQueryUtil(conn, updateContentSql, new Object[] { messageId })) {
                    dbUtil.executeUpdate();
                }
                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new SQLException("쪽지 읽음 상태 업데이트 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 특정 메시지 ID로 메시지 조회
    // 메시지 ID에 해당하는 메시지의 상세 정보를 조회하여 반환
    public MessageDTO getMessageById(int messageId, String userId) throws SQLException {
        String sql = "SELECT mc.*, ms.*, mf.file_id, mf.file_name, mf.file_path, " +
                "(SELECT us.userId FROM tbl_message_status us WHERE us.message_id = mc.message_id AND us.is_sender = 1) AS sender_id, "
                +
                "(SELECT us.userId FROM tbl_message_status us WHERE us.message_id = mc.message_id AND us.is_sender = 2) AS receiver_id "
                +
                "FROM tbl_message_content mc " +
                "JOIN tbl_message_status ms ON mc.message_id = ms.message_id " +
                "LEFT JOIN tbl_message_file mf ON mc.message_id = mf.message_id " +
                "WHERE mc.message_id = ? AND ms.userId = ?";

        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { messageId, userId })) {
            ResultSet rs = dbUtil.executeQuery();
            if (rs.next()) {
                MessageDTO message = new MessageDTO();
                message.setMessageId(rs.getInt("message_id"));
                message.setContent(rs.getString("content"));
                message.setSendDate(rs.getString("send_date"));
                message.setReadDate(rs.getString("read_date"));
                message.setMessageTitle(rs.getString("message_title"));
                message.setHasFileAttachment(rs.getInt("file_id") != 0);
                message.setSenderId(rs.getString("sender_id"));
                message.setReceiverId(rs.getString("receiver_id"));

                MessageStatusDTO status = new MessageStatusDTO();
                status.setUserId(rs.getString("userId"));
                status.setReadStatus(rs.getInt("read_status"));
                status.setIsSender(rs.getInt("is_sender"));
                message.setStatus(status);

                if (message.getHasFileAttachment()) {
                    MessageFileDTO file = new MessageFileDTO();
                    file.setFileName(rs.getString("file_name"));
                    file.setFilePath(rs.getString("file_path"));
                    message.setFile(file);
                }

                return message;
            }
        } catch (SQLException e) {
            throw new SQLException("메시지 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return null;
    }

    // 페이징 처리된 쪽지 목록 조회
    // 지정된 사용자의 메시지 목록을 페이징하여 조회
    public List<MessageDTO> getMessagesPaginated(String userId, int isSender, int page, int pageSize)
            throws SQLException {
        int offset = (page - 1) * pageSize;
        String sql = "SELECT mc.*, ms.*, mf.file_id, " +
                "(SELECT us.userId FROM tbl_message_status us WHERE us.message_id = mc.message_id AND us.is_sender != ms.is_sender) AS other_user_id, "
                +
                "(SELECT us.userId FROM tbl_message_status us WHERE us.message_id = mc.message_id AND us.is_sender = 1) AS sender_id, "
                +
                "(SELECT us.userId FROM tbl_message_status us WHERE us.message_id = mc.message_id AND us.is_sender = 2) AS receiver_id "
                +
                "FROM tbl_message_content mc " +
                "JOIN tbl_message_status ms ON mc.message_id = ms.message_id " +
                "LEFT JOIN tbl_message_file mf ON mc.message_id = mf.message_id " +
                "WHERE ms.userId = ? AND ms.is_sender = ? AND ms.delete_status = 0 " +
                "ORDER BY mc.send_date DESC LIMIT ? OFFSET ?";

        List<MessageDTO> messages = new ArrayList<>();

        try (Connection conn = DBConnectionManager.getConnection();
                DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, isSender, pageSize, offset })) {
            ResultSet rs = dbUtil.executeQuery();
            while (rs.next()) {
                MessageDTO message = new MessageDTO();
                message.setMessageId(rs.getInt("message_id"));
                message.setContent(rs.getString("content"));
                message.setSendDate(rs.getString("send_date"));
                message.setReadDate(rs.getString("read_date"));
                message.setMessageTitle(rs.getString("message_title"));

                MessageStatusDTO status = new MessageStatusDTO();
                status.setUserId(rs.getString("userId"));
                status.setReadStatus(rs.getInt("read_status"));
                status.setIsSender(rs.getInt("is_sender"));
                message.setStatus(status);

                message.setHasFileAttachment(rs.getInt("file_id") != 0);
                message.setOtherUserId(rs.getString("other_user_id"));

                message.setSenderId(rs.getString("sender_id"));
                message.setReceiverId(rs.getString("receiver_id"));

                messages.add(message);
            }
        } catch (SQLException e) {
            throw new SQLException("페이징된 메시지 목록을 조회하는 중 오류가 발생했습니다.");
        }

        return messages;
    }

    // 여러 메시지를 읽음 상태로 표시
    // 지정된 메시지 ID 목록의 메시지들을 읽음 상태로 변경
    public void markMessagesAsRead(String[] messageIds) throws SQLException {
        StringBuilder sql = new StringBuilder("UPDATE tbl_message_status SET read_status = 1 WHERE message_id IN (");
        for (int i = 0; i < messageIds.length; i++) {
            sql.append(i == 0 ? "?" : ", ?");
        }
        sql.append(")");

        try (Connection conn = DBConnectionManager.getConnection()) {
            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, sql.toString(), messageIds)) {
                dbUtil.executeUpdate();
            }
        } catch (SQLException e) {
            throw new SQLException("쪽지 읽음 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 여러 메시지 삭제
    // 지정된 메시지 ID 목록의 메시지들을 삭제 상태로 변경
    public int deleteMessages(String[] messageIds) throws SQLException {
        String deleteFilesSQL = "DELETE FROM tbl_message_file WHERE message_id IN (";
        String logicalDeleteSQL = "UPDATE tbl_message_status SET delete_status = 1 WHERE message_id IN (";
        for (int i = 0; i < messageIds.length; i++) {
            deleteFilesSQL += (i == 0 ? "?" : ", ?");
            logicalDeleteSQL += (i == 0 ? "?" : ", ?");
        }
        deleteFilesSQL += ")";
        logicalDeleteSQL += ")";

        String deleteProcedureCall = "CALL DeleteMessageIfBothDeleted(?)";
        int physicallyDeletedCount = 0;

        try (Connection conn = DBConnectionManager.getConnection()) {
            conn.setAutoCommit(false);
            try {
                // 먼저 관련된 파일 정보 삭제
                try (DbQueryUtil dbUtil = new DbQueryUtil(conn, deleteFilesSQL, messageIds)) {
                    dbUtil.executeUpdate();
                }

                // 논리 삭제
                try (DbQueryUtil dbUtil = new DbQueryUtil(conn, logicalDeleteSQL, messageIds)) {
                    dbUtil.executeUpdate();
                }

                // 각 메시지에 대해 실제 삭제 여부 판단을 위한 프로시저 호출
                for (String messageId : messageIds) {
                    try (DbQueryUtil dbUtil = new DbQueryUtil(conn, deleteProcedureCall,
                            new Object[] { Integer.parseInt(messageId) })) {
                        ResultSet rs = dbUtil.executeQuery();
                        if (rs.next() && rs.getInt(1) == 1) {
                            physicallyDeletedCount++;
                        }
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            throw new SQLException("쪽지 삭제 처리 중 오류가 발생했습니다: " + e.getMessage());
        }

        return physicallyDeletedCount;
    }

    // 전체 메시지 수 조회
    // 특정 사용자의 전체 메시지 수를 조회하여 반환
    public int getTotalMessageCount(String userId, int isSender) throws SQLException {
        String sql = "SELECT COUNT(*) FROM tbl_message_status WHERE userId = ? AND is_sender = ? AND delete_status = 0";
        try (Connection conn = DBConnectionManager.getConnection()) {
            try (DbQueryUtil dbUtil = new DbQueryUtil(conn, sql, new Object[] { userId, isSender })) {
                ResultSet rs = dbUtil.executeQuery();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            throw new SQLException("전체 쪽지 수 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return 0;
    }

}
