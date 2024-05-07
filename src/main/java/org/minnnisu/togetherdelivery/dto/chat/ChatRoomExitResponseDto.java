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
public class ChatRoomExitResponseDto {
    private Long chatRoomId;
    private String deletedMember;
    private LocalDateTime deletedAt;

    public static ChatRoomExitResponseDto fromEntity(ChatRoomMember chatRoomMember){
        return ChatRoomExitResponseDto.builder()
                .chatRoomId(chatRoomMember.getChatRoom().getId())
                .deletedMember(chatRoomMember.getUser().getUsername())
                .deletedAt(LocalDateTime.now())
                .build();
    }
}
