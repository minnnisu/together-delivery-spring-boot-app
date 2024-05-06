package org.minnnisu.togetherdelivery.repository;

import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
}
