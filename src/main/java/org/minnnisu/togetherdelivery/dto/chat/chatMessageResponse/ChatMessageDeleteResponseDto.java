package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.minnnisu.togetherdelivery.dto.chat.chatMessage.ChatMessageDeleteDto;

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
