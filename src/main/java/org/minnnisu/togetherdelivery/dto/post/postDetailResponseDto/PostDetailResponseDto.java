package org.minnnisu.togetherdelivery.dto.post.postDetailResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.PostImage;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostDetailResponseDto {
    PostDetailPostDto post;
    PostDetailChatRoomDto chatRoom;

    public static PostDetailResponseDto of(Post post, List<PostImage> images, boolean isPostCreator, boolean isChatRoomMember, ChatRoom chatRoom){
        return PostDetailResponseDto.builder()
                .post(PostDetailPostDto.of(post, images, isPostCreator))
                .chatRoom(PostDetailChatRoomDto.of(chatRoom, isChatRoomMember))
                .build();
    }
}
