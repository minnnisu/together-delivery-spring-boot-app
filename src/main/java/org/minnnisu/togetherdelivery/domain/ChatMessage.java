package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.*;
import lombok.*;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private ChatRoomMember sender;

    @Lob
    private String message;

    private ChatMessageType chatMessageType;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public static ChatMessage of(ChatRoomMember sender, String message, ChatMessageType chatMessageType) {
        return ChatMessage.builder()
                .chatRoom(sender.getChatRoom())
                .sender(sender)
                .message(message)
                .chatMessageType(chatMessageType)
                .build();
    }

    public static ChatMessage of(ChatRoomMember sender, ChatMessageType chatMessageType) {
        return ChatMessage.builder()
                .chatRoom(sender.getChatRoom())
                .sender(sender)
                .chatMessageType(chatMessageType)
                .build();
    }
}
