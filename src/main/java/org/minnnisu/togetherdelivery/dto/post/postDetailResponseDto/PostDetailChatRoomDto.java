package org.minnnisu.togetherdelivery.dto.post.postDetailResponseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoom;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostDetailChatRoomDto {
    private Long id;
    @JsonProperty("isChatRoomMember")
    private boolean isChatRoomMember;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

    public static PostDetailChatRoomDto of(ChatRoom chatRoom, boolean isChatRoomMember){
        return PostDetailChatRoomDto.builder()
                .id(chatRoom.getId())
                .isChatRoomMember(isChatRoomMember)
                .createdAt(chatRoom.getCreatedAt())
                .deletedAt(chatRoom.getDeletedAt())
                .build();
    }
}
