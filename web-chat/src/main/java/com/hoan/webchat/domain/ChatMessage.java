package com.hoan.webchat.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("chat_messages") // 테이블 매핑
public class ChatMessage {

    @Id
    private Long id;          // 메시지 ID (PK)
    private String roomId;    // 채팅방 ID
    private String sender;    // 메시지 보낸 사람
    private String content;   // 메시지 내용
    private String timestamp; // 전송 시간
}
