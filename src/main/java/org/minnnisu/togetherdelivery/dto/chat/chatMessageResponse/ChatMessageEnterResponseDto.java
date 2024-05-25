package org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;


@Getter
@Setter
@SuperBuilder
public class ChatMessageEnterResponseDto extends ChatMessageResponseDto {
    public static ChatMessageEnterResponseDto fromEntity(ChatMessage chatMessage, ChatRoomMember newChatRoomMember) {
        return ChatMessageEnterResponseDto.builder()
                .message(newChatRoomMember.getUser().getNickname() + "님이 채팅방에 초대되었습니다.")
                .type(ChatMessageType.ENTER)
                .sender(chatMessage.getSender().getUser().getNickname())
                .createdAt(chatMessage.getCreatedAt())
                .build();
    }
}
