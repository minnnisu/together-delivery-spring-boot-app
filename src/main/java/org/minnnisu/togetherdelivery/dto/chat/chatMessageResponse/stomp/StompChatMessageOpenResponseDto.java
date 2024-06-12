package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse.stomp;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;


@Getter
@Setter
@SuperBuilder
public class StompChatMessageOpenResponseDto extends StompChatMessageResponseDto {
    public static StompChatMessageOpenResponseDto fromEntity(ChatMessage chatMessage) {
        return StompChatMessageOpenResponseDto.builder()
                .message("채팅방이 생성되었습니다.")
                .type(ChatMessageType.OPEN)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
