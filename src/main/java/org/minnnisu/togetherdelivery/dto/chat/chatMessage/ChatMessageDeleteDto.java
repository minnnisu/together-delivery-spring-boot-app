package org.minnnisu.togetherdelivery.dto.chat.chatMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatMessageDeleteDto {
    private Long chatRoomId;
    private Long ChatMessageId;
    private String messageCreator;
    private ChatMessageType chatMessageType;
    private LocalDateTime deletedAt;


    public static ChatMessageDeleteDto fromEntity(ChatMessage chatMessage) {
        return ChatMessageDeleteDto.builder()
                .chatRoomId(chatMessage.getChatRoom().getId())
                .ChatMessageId(chatMessage.getId())
                .chatMessageType(chatMessage.getChatMessageType())
                .messageCreator(chatMessage.getSender().getUser().getNickname())
                .deletedAt(LocalDateTime.now())
                .build();
    }
}
