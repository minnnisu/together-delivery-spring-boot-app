package org.minnnisu.togetherdelivery.dto.stomp.stompChatMessage.response;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;


@Getter
@Setter
@SuperBuilder
public class StompChatMessageEnterResponseDto extends StompChatMessageResponseDto {
    public static StompChatMessageEnterResponseDto fromEntity(ChatMessage chatMessage, ChatRoomMember newChatRoomMember) {
        return StompChatMessageEnterResponseDto.builder()
                .messageId(chatMessage.getId())
                .message(newChatRoomMember.getUser().getNickname() + "님이 채팅방에 초대되었습니다.")
                .type(ChatMessageType.ENTER)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }

    public static StompChatMessageEnterResponseDto fromEntity(ChatMessage chatMessage) {
        return StompChatMessageEnterResponseDto.builder()
                .messageId(chatMessage.getId())
                .message(chatMessage.getSender().getUser().getNickname() + "님이 채팅방에 초대되었습니다.")
                .type(ChatMessageType.ENTER)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
