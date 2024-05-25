package org.minnnisu.togetherdelivery.dto.chat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatRoomInviteDto {
    private Long chatRoomId;
    private String invitedMember;
    private LocalDateTime createdAt;
    private ChatMessageDto chatMessage;

    public static ChatRoomInviteDto of(ChatRoomMember newChatRoomMember, ChatMessageDto chatMessage) {
        return ChatRoomInviteDto.builder()
                .chatRoomId(newChatRoomMember.getChatRoom().getId())
                .invitedMember(newChatRoomMember.getUser().getNickname())
                .createdAt(newChatRoomMember.getCreatedAt())
                .chatMessage(chatMessage)
                .build();
    }
}
