package org.minnnisu.togetherdelivery.dto.chat;

import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;

@Getter
@Setter
public class ChatMessageRequestDto {
    private ChatMessageType type;

    private Long chatRoomId;

    private Long senderUsername;

    private Long chatMessageId;

    private String message;
}
