package org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomInvite;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatRoomInviteResponseDto {
    private Long chatRoomId;
    private String invitedMember;
    private LocalDateTime createdAt;

    public static ChatRoomInviteResponseDto fromDto(ChatRoomInviteDto chatRoomInviteDto){
        return ChatRoomInviteResponseDto.builder()
                .chatRoomId(chatRoomInviteDto.getChatRoomId())
                .invitedMember(chatRoomInviteDto.getInvitedMember())
                .createdAt(chatRoomInviteDto.getCreatedAt())
                .build();
    }
}
