package org.minnnisu.togetherdelivery.dto.stomp.stompChatMessage;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.minnnisu.togetherdelivery.dto.stomp.chatMessage.chatMessageDelete.ChatMessageDeleteDto;


@Getter
@Setter
@SuperBuilder
public class StompChatMessageDeleteResponseDto extends StompChatMessageResponseDto {
    private Long deletedChatMessageId;

    public static StompChatMessageDeleteResponseDto fromEntity(ChatMessage chatMessage) {
        return StompChatMessageDeleteResponseDto.builder()
                .message(chatMessage.getSender().getUser().getNickname() + "님의 메시지가 삭제되었습니다.")
                .deletedChatMessageId(chatMessage.getId())
                .type(ChatMessageType.DELETE)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }

    public static StompChatMessageDeleteResponseDto fromEntity(ChatMessageDeleteDto chatMessageDeleteDto) {
        return StompChatMessageDeleteResponseDto.builder()
                .message(chatMessageDeleteDto.getMessageCreator() + "님의 메시지가 삭제되었습니다.")
                .deletedChatMessageId(chatMessageDeleteDto.getChatMessageId())
                .type(chatMessageDeleteDto.getChatMessageType())
                .sender(chatMessageDeleteDto.getMessageCreator())
                .createdAt(chatMessageDeleteDto.getDeletedAt())
                .build();
    }
}
