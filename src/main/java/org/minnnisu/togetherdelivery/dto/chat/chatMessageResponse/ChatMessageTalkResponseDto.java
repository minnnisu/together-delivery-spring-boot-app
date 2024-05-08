package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;


@Getter
@Setter
@SuperBuilder
public class ChatMessageTalkResponseDto extends ChatMessageResponseDto {
    private String message;

    public static ChatMessageTalkResponseDto fromEntity(ChatMessage chatMessage) {
        return ChatMessageTalkResponseDto.builder()
                .message(chatMessage.getMessage())
                .type(ChatMessageType.TALK)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
