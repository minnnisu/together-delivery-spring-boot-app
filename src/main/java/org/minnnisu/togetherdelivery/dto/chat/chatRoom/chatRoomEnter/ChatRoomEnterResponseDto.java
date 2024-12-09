package org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomEnter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatRoomEnterResponseDto {
    private Long chatRoomId;
    private String newMemberId;
    private LocalDateTime createdAt;

    public static ChatRoomEnterResponseDto fromDto(ChatRoomEnterDto chatRoomEnterDto){
        return ChatRoomEnterResponseDto.builder()
                .chatRoomId(chatRoomEnterDto.getChatRoomId())
                .newMemberId(chatRoomEnterDto.getNewMemberId())
                .createdAt(chatRoomEnterDto.getCreatedAt())
                .build();
    }
}
