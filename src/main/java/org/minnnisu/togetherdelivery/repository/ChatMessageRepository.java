package org.minnnisu.togetherdelivery.repository;

import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findAllBySender(ChatRoomMember sender);

    @Query("SELECT m FROM ChatMessage m WHERE m.chatRoom = :chatRoom ORDER BY m.id DESC")
    Page<ChatMessage> findLatestMessagesByChatRoom(ChatRoom chatRoom, Pageable pageable);

    @Query("SELECT m FROM ChatMessage m WHERE m.chatRoom = :chatRoom AND m.id < :cursor ORDER BY m.id DESC")
    Page<ChatMessage> getChatMessagesByCursor(ChatRoom chatRoom, Long cursor, Pageable chatMessagePageable);


}
