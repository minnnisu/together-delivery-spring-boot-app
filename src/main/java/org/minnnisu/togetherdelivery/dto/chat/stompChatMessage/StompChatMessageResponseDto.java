package org.minnnisu.togetherdelivery.dto.chat.stompChatMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class StompChatMessageResponseDto {
    private ChatMessageType type;

    private String sender;

    private String message;

    private LocalDateTime createdAt;

    public static StompChatMessageResponseDto of(ChatMessageType type, String sender, String message, LocalDateTime createdAt) {
        return StompChatMessageResponseDto.builder()
                .type(type)
                .sender(sender)
                .message(message)
                .createdAt(createdAt)
                .build();
    }
}
