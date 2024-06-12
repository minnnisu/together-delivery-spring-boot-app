package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse.stomp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;


@Getter
@Setter
@SuperBuilder
public class StompChatMessageLeaveResponseDto extends StompChatMessageResponseDto {
    public static StompChatMessageLeaveResponseDto fromEntity(ChatMessage chatMessage) {
        return StompChatMessageLeaveResponseDto.builder()
                .message(chatMessage.getSender().getUser().getNickname() + "님이 채팅방을 떠났습니다.")
                .type(ChatMessageType.LEAVE)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
