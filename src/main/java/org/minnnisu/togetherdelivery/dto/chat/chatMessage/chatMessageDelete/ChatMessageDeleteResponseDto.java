package org.minnnisu.togetherdelivery.dto.chat.chatMessage.chatMessageDelete;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatMessageDeleteResponseDto {
    private Long chatRoomId;
    private Long ChatMessageId;
    private String messageCreator;
    private ChatMessageType chatMessageType;
    private LocalDateTime deletedAt;


    public static ChatMessageDeleteResponseDto fromDto(ChatMessageDeleteDto chatMessageDeleteDto) {
        return ChatMessageDeleteResponseDto.builder()
                .chatRoomId(chatMessageDeleteDto.getChatRoomId())
                .ChatMessageId(chatMessageDeleteDto.getChatMessageId())
                .chatMessageType(chatMessageDeleteDto.getChatMessageType())
                .messageCreator(chatMessageDeleteDto.getMessageCreator())
                .deletedAt(chatMessageDeleteDto.getDeletedAt())
                .build();
    }
}
