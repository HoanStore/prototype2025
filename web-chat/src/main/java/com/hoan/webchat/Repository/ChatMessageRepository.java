package com.hoan.webchat.Repository;

import com.hoan.webchat.domain.ChatMessage;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ChatMessageRepository extends ReactiveCrudRepository<ChatMessage, Long> {
    Flux<ChatMessage> findByRoomId(String roomId);
}
