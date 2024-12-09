package org.minnnisu.togetherdelivery.dto.stomp.stompChatMessage.request;

import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;

@Getter
@Setter
public class StompChatMessageRequestDto {
    private ChatMessageType type;

    private Long chatRoomId;

    private String username;

    private Long deleteTargetChatMessageId;

    private String message;
}
