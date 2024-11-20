package net.fullstack7.edusecond.edusecond.config;

import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.fullstack7.edusecond.edusecond.dto.message.ChatMessageDTO;
import net.fullstack7.edusecond.edusecond.service.message.ChatServiceIf;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Log4j2
@RequiredArgsConstructor
public class ChatWebSocketHandler extends TextWebSocketHandler {
    
    private final Map<String, Map<WebSocketSession, String>> roomSessions = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();
    private final ChatServiceIf chatService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        try {
            String query = session.getUri().getQuery();
            log.info("웹소켓 연결 시도. URI: {}, Query: {}", session.getUri(), query);
            
            String roomId = query.split("=")[1];
            roomSessions.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
            roomSessions.get(roomId).put(session, session.getId());
            log.info("웹소켓 연결 성공: {}, 방번호: {}", session.getId(), roomId);
        } catch (Exception e) {
            log.error("웹소켓 연결 중 에러 발생", e);
        }
    }

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

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.error("웹소켓 에러 발생", exception);
    }
}
