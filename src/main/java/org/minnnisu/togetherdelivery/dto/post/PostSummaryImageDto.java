package org.minnnisu.togetherdelivery.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.PostImage;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostSummaryImageDto {
    private Long id;

    private String imageName;

    private LocalDateTime createdAt;

    public static PostSummaryImageDto fromEntity(PostImage postImage){
        return PostSummaryImageDto.builder()
                .id(postImage.getId())
                .imageName(postImage.getImageName())
                .createdAt(postImage.getCreatedAt())
                .build();
    }
}
