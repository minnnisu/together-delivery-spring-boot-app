package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse.stomp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;


@Getter
@Setter
@SuperBuilder
public class StompChatMessageTalkResponseDto extends StompChatMessageResponseDto {
    private Long chatMessageId;

    public static StompChatMessageTalkResponseDto fromEntity(ChatMessage chatMessage) {
        return StompChatMessageTalkResponseDto.builder()
                .chatMessageId(chatMessage.getId())
                .message(chatMessage.getMessage())
                .type(ChatMessageType.TALK)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
