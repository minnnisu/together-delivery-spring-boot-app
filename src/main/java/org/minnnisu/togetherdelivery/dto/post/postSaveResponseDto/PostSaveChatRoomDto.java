package org.minnnisu.togetherdelivery.dto.post.postSaveResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostSaveChatRoomDto {
    private Long id;
    private String creatorUsername;
    private LocalDateTime createdAt;

    public static PostSaveChatRoomDto of(ChatRoomMember chatRoomCreator){
        ChatRoom chatRoom = chatRoomCreator.getChatRoom();

        return PostSaveChatRoomDto.builder()
                .id(chatRoom.getId())
                .creatorUsername(chatRoomCreator.getUser().getUsername())
                .createdAt(chatRoom.getCreatedAt())
                .build();
    }
}
