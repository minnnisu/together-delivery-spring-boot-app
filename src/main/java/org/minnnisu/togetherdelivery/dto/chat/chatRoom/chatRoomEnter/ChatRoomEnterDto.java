package org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomEnter;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.minnnisu.togetherdelivery.dto.stomp.chatMessage.ChatMessageDto;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatRoomEnterDto {
    private Long chatRoomId;
    private String newMemberId;
    private LocalDateTime createdAt;
    private ChatMessageDto chatMessage;

    public static ChatRoomEnterDto of(ChatRoomMember newChatRoomMember, ChatMessageDto chatMessage) {
        return ChatRoomEnterDto.builder()
                .chatRoomId(newChatRoomMember.getChatRoom().getId())
                .newMemberId(newChatRoomMember.getUser().getNickname())
                .createdAt(newChatRoomMember.getCreatedAt())
                .chatMessage(chatMessage)
                .build();
    }
}
