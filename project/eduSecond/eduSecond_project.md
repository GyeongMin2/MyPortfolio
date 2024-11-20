## 주요 소스코드

### 채팅 기능

#### WebSocketConfig.java
스프링 웹소켓 설정 클래스입니다.

```java
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///home/gyeongmini/uploads/")
                .setCachePeriod(3600);
                
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");
    }
}
```
### WebSocket 구현 방식

#### TextWebSocketHandler 선택 이유
Spring에서 제공하는 TextWebSocketHandler를 상속받아 구현했습니다. 일반 WebSocket 구현 대신 TextWebSocketHandler를 선택한 이유는 다음과 같습니다:

1. **추상화된 메시지 처리**
   - WebSocket의 저수준 구현 대신 Spring의 높은 추상화 계층 활용
   - 텍스트 기반 메시지 처리에 최적화된 인터페이스 제공
   ```java:ChatWebSocketHandler.java
   //startLine: 42
   //endLine: 71
   @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        try {
            String roomId = session.getUri().getQuery().split("=")[1];
            ChatMessageDTO chatMessage = gson.fromJson(message.getPayload(), ChatMessageDTO.class);
            
            log.info("메시지 수신: {}", chatMessage);
            
            chatService.saveMessage(chatMessage);
            
            Map<WebSocketSession, String> sessions = roomSessions.get(roomId);
            if (sessions != null) {
                String messageJson = gson.toJson(chatMessage);
                sessions.keySet().forEach(s -> {
                    try {
                        if (s.isOpen()) {
                            s.sendMessage(new TextMessage(messageJson));
                            log.info("메시지 전송 성공: {} -> {}", session.getId(), s.getId());
                        }
                    } catch (Exception e) {
                        log.error("메시지 전송 실패: {} -> {}", session.getId(), s.getId(), e);
                    }
                });
            } else {
                log.warn("해당 방의 세션이 없음: {}", roomId);
            }
        } catch (Exception e) {
            log.error("메시지 처리 중 에러 발생", e);
            throw e;
        }
    }
   ```

2. **생명주기 관리**
   - 연결, 메시지 처리, 종료 등 세션 생명주기를 명확하게 관리
   - Spring의 DI(의존성 주입)를 통한 효율적인 리소스 관리
   ```java:ChatWebSocketHandler.java
   //startLine: 73
   //endLine: 83
   @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String roomId = session.getUri().getQuery().split("=")[1];
        if (roomSessions.containsKey(roomId)) {
            roomSessions.get(roomId).remove(session);
            if (roomSessions.get(roomId).isEmpty()) {
                roomSessions.remove(roomId);
            }
        }
        log.info("웹소켓 연결 종료: {}", session.getId());
    }
   ```

3. **예외 처리 및 로깅**
   - Spring의 통합된 예외 처리 메커니즘 활용
   - 체계적인 로깅 시스템 구현 용이
   ```java:ChatWebSocketHandler.java
   //startLine: 85
   //endLine: 88
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("웹소켓 에러 발생", exception);
    }
   ```

4. **확장성**
   - Spring의 다른 컴포넌트들과 쉽게 통합
   - 채팅방 관리, 메시지 저장 등 부가 기능 구현이 용이

##### 일반 WebSocket과의 차이점
- 일반 WebSocket: 저수준 구현으로 모든 기능을 직접 구현해야 함
- TextWebSocketHandler: Spring의 웹소켓 추상화 계층을 활용하여 높은 수준의 API 제공

#### ChatMapper.xml [(ChatMapper.xml)](https://github.com/GyeongMin2/MyPortfolio/tree/main/project/eduSecond/src/main/resources/mapper/ChatMapper.xml)
채팅 메시지 저장 쿼리입니다.

```xml
<select id="selectChatRoomList" resultType="net.fullstack7.edusecond.edusecond.dto.message.ChatRoomDTO">
        SELECT 
            c.roomId,
            c.productId,
            c.sellerId,
            c.buyerId,
            p.productName,
            pi.imagePath as productImage,
            m.userName as otherUserName,
            lcm.message as lastMessage,
            lcm.regDate as lastMessageDate,
            COALESCE((SELECT COUNT(*) 
             FROM tbl_chat_message 
             WHERE roomId = c.roomId 
             AND isRead = 'N' 
             AND senderId != #{userId}), 0) as unreadCount
        FROM tbl_chatroom c
        LEFT JOIN tbl_product p ON c.productId = p.productId
        LEFT JOIN tbl_product_image pi ON p.productId = pi.productId AND pi.isMain = 'Y'
        LEFT JOIN tbl_member m ON 
            CASE 
                WHEN c.sellerId = #{userId} THEN m.userId = c.buyerId
                ELSE m.userId = c.sellerId
            END
        LEFT JOIN (
            SELECT cm1.roomId, cm1.message, cm1.regDate
            FROM tbl_chat_message cm1
            WHERE cm1.messageId = (
                SELECT cm2.messageId
                FROM tbl_chat_message cm2
                WHERE cm1.roomId = cm2.roomId
                ORDER BY cm2.regDate DESC, cm2.messageId DESC
                LIMIT 1
            )
        ) lcm ON c.roomId = lcm.roomId
        WHERE (c.sellerId = #{userId} AND c.sellerActive = 'Y') 
           OR (c.buyerId = #{userId} AND c.buyerActive = 'Y')
        ORDER BY 
            CASE 
                WHEN lcm.regDate IS NULL THEN 1 
                ELSE 0 
            END,
            lcm.regDate DESC
    </select>

    <!-- 채팅방 단일 조회 -->
    <select id="selectChatRoom" resultType="net.fullstack7.edusecond.edusecond.dto.message.ChatRoomDTO">
        SELECT 
            cr.roomId, cr.productId, cr.sellerId, cr.buyerId,
            p.productName,
            pi.imagePath as productImage,
            m.userName as otherUserName
        FROM tbl_chatroom cr
        LEFT JOIN tbl_product p ON cr.productId = p.productId
        LEFT JOIN tbl_product_image pi ON p.productId = pi.productId AND pi.isMain = 'Y'
        LEFT JOIN tbl_member m ON 
            CASE 
                WHEN cr.sellerId = #{userId} THEN m.userId = cr.buyerId
                ELSE m.userId = cr.sellerId
            END
        WHERE cr.roomId = #{roomId}
        AND (
            (cr.sellerId = #{userId} AND cr.sellerActive = 'Y')
            OR 
            (cr.buyerId = #{userId} AND cr.buyerActive = 'Y')
        )
    </select>

    <!-- 채팅 메시지 목록 조회 -->
    <select id="selectChatMessages" resultType="net.fullstack7.edusecond.edusecond.dto.message.ChatMessageDTO">
        SELECT 
            m.*,
            mb.userName as senderName
        FROM tbl_chat_message m
        LEFT JOIN tbl_member mb ON m.senderId = mb.userId
        WHERE m.roomId = #{roomId}
        ORDER BY m.messageId ASC
    </select>

    <!-- 메시지 읽음 처리 -->
    <update id="updateMessageRead">
        UPDATE tbl_chat_message
        SET isRead = 'Y'
        WHERE roomId = #{roomId}
        AND senderId != #{userId}
        AND isRead = 'N'
    </update>

    <!-- 채팅방 존재 여부 확인 -->
    <select id="findExistingChatRoom" resultType="net.fullstack7.edusecond.edusecond.dto.message.ChatRoomDTO">
        SELECT roomId, productId, sellerId, buyerId
        FROM tbl_chatroom
        WHERE productId = #{productId}
        AND sellerId = #{sellerId}
        AND buyerId = #{buyerId}
        AND sellerActive = 'Y'
        AND buyerActive = 'Y'
        LIMIT 1
    </select>

    <!-- 읽지 않은 메시지 수 조회 -->
    <select id="getUnreadCount" resultType="int">
        SELECT COUNT(*) as unreadCount
        FROM tbl_chat_message m
        JOIN tbl_chatroom r ON m.roomId = r.roomId
        WHERE m.isRead = 'N'
        AND m.senderId != #{userId}
        AND (
            (r.sellerId = #{userId} AND r.sellerActive = 'Y')
            OR 
            (r.buyerId = #{userId} AND r.buyerActive = 'Y')
        )
        AND r.roomId IN (
            SELECT roomId 
            FROM tbl_chatroom 
            WHERE (sellerId = #{userId} AND sellerActive = 'Y')
               OR (buyerId = #{userId} AND buyerActive = 'Y')
        )
    </select>
```

#### 프론트엔드 소스코드 [((chatting.jsp)](https://github.com/GyeongMin2/MyPortfolio/tree/main/project/onlineLecture/src/main/webapp/WEB-INF/views/chatting.jsp)

웹소켓 연결 및 메시지 처리 소스코드입니다.
```javascript
let ws;

function connect() {
    ws = new WebSocket(`ws://${window.location.host}/es/ws/chat?roomId=${roomId}`);
    ws.onmessage = onMessage;
    ws.onerror = (error) => {
        console.error('WebSocket Error:', error);
    };
}

function onMessage(event) {
    const message = JSON.parse(event.data);

    if (message.type === 'READ') {
        document.querySelectorAll('.unread-status').forEach(status => {
            status.textContent = '읽음';
            status.classList.remove('unread-status');
            status.classList.add('read-status');
        });
    } else if (message.type === 'CHAT') {
        addMessage(message);

        // 상대방 메시지 읽음 처리
        if (message.senderId !== userId) {
            const readMessage = {
                type: 'READ',
                roomId: roomId,
                senderId: userId,
                targetId: message.senderId
            };

            if (ws && ws.readyState === WebSocket.OPEN) {
                ws.send(JSON.stringify(readMessage));
            }
        }
    }
}
```

메시지 전송 및 하이브리드 통신 소스코드입니다.
```javascript
function sendMessage() {
    const messageInput = document.getElementById('messageInput');
    const message = messageInput.value.trim();
    
    if (message) {
        const chatMessage = {
            type: 'CHAT',
            roomId: roomId,
            senderId: userId,
            senderName: userName,
            message: message
        };

        // WebSocket으로 전송 시도
        if (ws && ws.readyState === WebSocket.OPEN) {
            ws.send(JSON.stringify(chatMessage));
        } else {
            // WebSocket 연결이 없으면 HTTP로 전송
            fetch('${pageContext.request.contextPath}/message/send', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(chatMessage)
            })
            .then(() => {
                addMessage(chatMessage);
            })
            .catch(error => {
                console.error('Error:', error);
                alert('메시지 전송에 실패했습니다.');
            });
        }
        
        messageInput.value = '';
    }
}
```
#### 주요 기능
- 웹소켓 연결 및 메시지 처리
- 메시지 전송 및 하이브리드 통신
- 읽지 않은 메시지 수 조회

### Class Diagram & ERD
![classDiagram](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/eduSecond/eduSecond_classdiagram.png)

![erd](https://github.com/GyeongMin2/MyPortfolio/blob/main/images/eduSecond/eduSecond_ERD.png)