package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse;

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
public class ChatMessageResponseDto {
    private ChatMessageType type;

    private String sender;

    private LocalDateTime createdAt;
}
