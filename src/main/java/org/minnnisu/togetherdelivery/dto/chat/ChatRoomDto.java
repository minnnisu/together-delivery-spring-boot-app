package org.minnnisu.togetherdelivery.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.minnnisu.togetherdelivery.domain.Post;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatRoomDto {
    private Long chatRoomId;

    private Long postId;

    private LocalDateTime createdAt;

    public static ChatRoomDto fromEntity(ChatRoom chatRoom) {
        return ChatRoomDto.builder()
                .chatRoomId(chatRoom.getId())
                .postId(chatRoom.getPost().getId())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }

    public static ChatRoomDto fromEntity(ChatRoomMember chatRoomMember) {
        ChatRoom chatRoom = chatRoomMember.getChatRoom();
        return ChatRoomDto.builder()
                .chatRoomId(chatRoom.getId())
                .postId(chatRoom.getPost().getId())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}