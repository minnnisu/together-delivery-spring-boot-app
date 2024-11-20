package org.minnnisu.togetherdelivery.dto.post.postDetailResponseDto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
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
public class PostDetailPostDto {
    private Long id;
    private String nickname;
    private String content;
    private String restaurantName;
    private String categoryCode;
    private int deliveryFee;
    private int minOrderFee;
    private PostLocationDto meetLocation;
    private boolean status;
    @JsonProperty("isPostCreator")
    private boolean isPostCreator;
    private List<PostSummaryImageDto> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public static PostDetailPostDto of(Post post, List<PostImage> images, boolean isPostCreator){
        return PostDetailPostDto.builder()
                .id(post.getId())
                .nickname(post.getUser().getNickname())
                .content(post.getContent())
                .restaurantName(post.getRestaurantName())
                .categoryCode(post.getCategory().getCategoryCode())
                .deliveryFee(post.getDeliveryFee())
                .minOrderFee(post.getMinOrderFee())
                .meetLocation(PostLocationDto.fromEntity(post))
                .status(post.isStatus())
                .isPostCreator(isPostCreator)
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .images(images.stream().map(PostSummaryImageDto::fromEntity).toList())
                .build();
    }
}
