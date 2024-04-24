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
    private PostListMetaData metaData;
    private List<PostSummaryDto> posts;

    public static PostListResponseDto fromPage(Page<Post> postPage){
        return PostListResponseDto.builder()
                .metaData(PostListMetaData.of(postPage.getTotalPages(), postPage.getNumber()))
                .posts(postPage.map(PostSummaryDto::fromEntity).toList())
                .build();
    }
}
