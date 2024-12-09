package org.minnnisu.togetherdelivery.dto.post.postStatusToggle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.minnnisu.togetherdelivery.domain.Post;

@Getter
@AllArgsConstructor
@Builder
public class PostStatusToggleDto {
    private Long postId;
    private boolean status;

    public static PostStatusToggleDto fromEntity(Post post) {
        return PostStatusToggleDto.builder()
                .postId(post.getId())
                .status(post.isStatus())
                .build();
    }
}
