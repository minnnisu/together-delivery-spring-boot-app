package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;


@Getter
@Setter
@SuperBuilder
public class ChatMessageLeaveResponseDto extends ChatMessageResponseDto {
    public static ChatMessageLeaveResponseDto fromEntity(ChatMessage chatMessage) {
        return ChatMessageLeaveResponseDto.builder()
                .type(ChatMessageType.LEAVE)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
