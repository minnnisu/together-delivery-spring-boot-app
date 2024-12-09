package org.minnnisu.togetherdelivery.dto.post.postStatusToggle;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.minnnisu.togetherdelivery.domain.Post;

@Getter
@AllArgsConstructor
@Builder
public class PostStatusToggleResponseDto {
    private Long postId;
    private boolean status;

    public static PostStatusToggleResponseDto fromDto(PostStatusToggleDto dto) {
        return PostStatusToggleResponseDto.builder()
                .postId(dto.getPostId())
                .status(dto.isStatus())
                .build();
    }
}
