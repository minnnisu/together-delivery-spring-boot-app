package org.minnnisu.togetherdelivery.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Post;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostListResponseDto {
    private int totalPage;
    private int currentPage;
    private List<PostDto> posts;

    public static PostListResponseDto fromPage(Page<Post> postPage){
        return PostListResponseDto.builder()
                .totalPage(postPage.getTotalPages())
                .currentPage(postPage.getNumber())
                .posts(postPage.map(PostDto::fromEntity).toList())
                .build();
    }
}
