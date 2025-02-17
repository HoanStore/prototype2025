package com.hoan.webchat.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.WebSocketMessage;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class ChatWebSocketHandler implements WebSocketHandler {

    // 연결된 모든 세션을 저장할 Set
    private static final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();

    /**
     * 클라이언트가 WebSocket 서버에 연결된 후 호출됩니다. 연결이 성공하면 여기에 필요한 초기 작업을 수행할 수 있습니다.
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 클라이언트가 연결될 때 세션을 저장
        sessions.add(session);
        System.out.println("WebSocket 연결 성공: " + session.getId());

        // 새로운 클라이언트에게 인사 메시지 보내기
        session.sendMessage(new TextMessage("Welcome to the chat!"));
    }

    /**
     * 클라이언트가 메시지를 보낼 때 호출됩니다. 이 메소드에서 메시지를 처리하고 응답을 보낼 수 있습니다.
     * @param session
     * @param message
     * @throws Exception
     */
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        // 클라이언트가 보낸 메시지 처리
        String incomingMessage = (String) message.getPayload();
        System.out.println("Received message: " + incomingMessage);

        // 받은 메시지를 모든 클라이언트에게 브로드캐스트
        broadcastMessage(session.getId() + " : " + incomingMessage);
    }

    /**
     * WebSocket 연결 중 오류가 발생하면 호출됩니다. 오류를 로깅하거나, 필요한 오류 처리 로직을 구현할 수 있습니다.
     * @param session
     * @param exception
     * @throws Exception
     */
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        System.err.println("WebSocket 오류 발생: " + exception.getMessage());
    }

    /**
     * 클라이언트와의 WebSocket 연결이 종료되면 호출됩니다. 연결이 종료되었을 때 필요한 작업(세션 정리 등)을 처리합니다.
     * @param session
     * @param closeStatus
     * @throws Exception
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        // 클라이언트가 연결을 종료하면 세션을 제거
        sessions.remove(session);
        System.out.println("WebSocket 연결 종료: " + session.getId());
    }

    /**
     * WebSocket에서 부분 메시지를 처리할지 여부를 설정하는 메소드입니다. 일반적으로 false를 반환합니다.
     * @return
     */
    @Override
    public boolean supportsPartialMessages() {
        return false; // 전체 메시지만 처리
    }

    /**
     * 모든 클라이언트에게 메시지를 브로드캐스트하는 메소드
     * @param message
     */
    private void broadcastMessage(String message) {
        for (WebSocketSession session : sessions) {
            try {
                session.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                System.err.println("Error sending message to session " + session.getId() + ": " + e.getMessage());
            }
        }
    }
}
