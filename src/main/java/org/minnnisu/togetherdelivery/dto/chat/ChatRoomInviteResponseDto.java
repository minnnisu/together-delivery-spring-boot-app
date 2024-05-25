package org.minnnisu.togetherdelivery.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.List;

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
