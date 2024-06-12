package org.minnnisu.togetherdelivery.dto.chat.chatMessage;

import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;

@Getter
@Setter
public class ChatMessageRequestDto {
    private ChatMessageType type;

    private Long chatRoomId;

    private String username;

    private Long deleteTargetChatMessageId;

    private String message;
}
