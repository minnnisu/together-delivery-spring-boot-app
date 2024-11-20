package org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatRoomListResponseDto {
    private List<ChatRoomDto> chatRooms;

    public static ChatRoomListResponseDto fromEntity(List<ChatRoomMember> chatRoomMembers) {
        return ChatRoomListResponseDto.builder()
                .chatRooms(chatRoomMembers.stream().map(ChatRoomDto::fromEntity).toList())
                .build();
    }

    public static ChatRoomListResponseDto of(List<ChatRoomDto> chatRooms) {
        return ChatRoomListResponseDto.builder()
                .chatRooms(chatRooms)
                .build();
    }
}
