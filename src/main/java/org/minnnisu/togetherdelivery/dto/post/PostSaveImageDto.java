package org.minnnisu.togetherdelivery.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.PostImage;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostSaveImageDto {
    private Long id;

    private String imageName;

    private LocalDateTime createdAt;

    public static PostSaveImageDto fromEntity(PostImage postImage){
        return PostSaveImageDto.builder()
                .id(postImage.getId())
                .imageName(postImage.getImageName())
                .createdAt(postImage.getCreatedAt())
                .build();
    }
}
