package org.minnnisu.togetherdelivery.dto.post.postSaveResponseDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.ChatRoom;
import org.minnnisu.togetherdelivery.domain.ChatRoomMember;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.PostImage;
import org.minnnisu.togetherdelivery.dto.post.PostLocationDto;
import org.minnnisu.togetherdelivery.dto.post.PostSummaryImageDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostSaveResponseDto {
    PostSavePostDto post;
    PostSaveChatRoomDto chatRoom;

    public static PostSaveResponseDto of(Post post, List<PostImage> postImages, ChatRoomMember chatRoomCreator) {
        return PostSaveResponseDto.builder()
                .post(PostSavePostDto.of(post, postImages))
                .chatRoom(PostSaveChatRoomDto.of(chatRoomCreator))
                .build();
    }
}
