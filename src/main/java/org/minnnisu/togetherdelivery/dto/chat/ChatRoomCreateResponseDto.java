package org.minnnisu.togetherdelivery.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatRoomCreateResponseDto {
    private Long chatRoomId;
    private Long postId;
    private String creator;
    private LocalDateTime createdAt;

    public static ChatRoomCreateResponseDto fromEntity(ChatRoomMember chatRoomMember){
        ChatRoom chatRoom = chatRoomMember.getChatRoom();

        return ChatRoomCreateResponseDto.builder()
                .chatRoomId(chatRoom.getId())
                .postId(chatRoom.getPost().getId())
                .creator(chatRoomMember.getUser().getUsername())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }

    public static ChatRoomCreateResponseDto of(Long chatRoomId, long postId, String creator, LocalDateTime createdAt) {
     return ChatRoomCreateResponseDto.builder()
             .chatRoomId(chatRoomId)
             .postId(postId)
             .creator(creator)
             .createdAt(createdAt)
             .build();
    }
}
