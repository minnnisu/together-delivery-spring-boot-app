package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;


@Getter
@Setter
@SuperBuilder
public class ChatMessageEnterResponseDto extends ChatMessageResponseDto {
    public static ChatMessageEnterResponseDto fromEntity(ChatMessage chatMessage) {
        return ChatMessageEnterResponseDto.builder()
                .type(ChatMessageType.ENTER)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
