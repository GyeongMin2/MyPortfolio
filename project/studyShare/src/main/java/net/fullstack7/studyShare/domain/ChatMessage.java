package net.fullstack7.studyShare.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "TEXT COMMENT '메시지'")
    private String message;
    @Column(columnDefinition = "DATETIME COMMENT '메시지 전송 일'")
    private LocalDateTime createdAt;
    @Column(columnDefinition = "TINYINT(1) DEFAULT 0 COMMENT '0: 읽지 않음, 1: 읽음'")
    private Integer isRead;

    @ManyToOne
    @JoinColumn(name = "chatRoomId")
    private ChatRoom chatRoom;

    @Column(columnDefinition = "VARCHAR(20) not null comment '보낸사람 아이디'")
    private String senderId;
}
