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
    private List<String> members;
    private LocalDateTime createdAt;

    public static ChatRoomCreateResponseDto fromEntity(List<ChatRoomMember> chatRoomMembers){
        ChatRoom chatRoom = chatRoomMembers.get(0).getChatRoom();

        return ChatRoomCreateResponseDto.builder()
                .chatRoomId(chatRoom.getId())
                .postId(chatRoom.getPost().getId())
                .members(chatRoomMembers.stream().map(chatRoomMember -> chatRoomMember.getUser().getUsername()).toList())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}
