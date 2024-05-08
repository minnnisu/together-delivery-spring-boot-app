package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;


@Getter
@Setter
@SuperBuilder
public class ChatMessageDeleteResponseDto extends ChatMessageResponseDto {
    private Long chatMessageId;

    public static ChatMessageDeleteResponseDto fromEntity(ChatMessage chatMessage) {
        return ChatMessageDeleteResponseDto.builder()
                .chatMessageId(chatMessage.getId())
                .type(ChatMessageType.DELETE)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
