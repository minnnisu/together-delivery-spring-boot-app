package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;


@Getter
@Setter
@SuperBuilder
public class ChatMessageOpenResponseDto extends ChatMessageResponseDto {
    public static ChatMessageOpenResponseDto fromEntity(ChatMessage chatMessage) {
        return ChatMessageOpenResponseDto.builder()
                .type(ChatMessageType.OPEN)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
