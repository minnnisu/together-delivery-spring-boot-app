package org.minnnisu.togetherdelivery.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Post;
import org.minnnisu.togetherdelivery.domain.PostImage;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostDetailResponseDto {
    private Long id;
    private String nickname;
    private String title;
    private String content;
    private String restaurantName;
    private String categoryCode;
    private int deliveryFee;
    private int minOrderFee;
    private PostLocationDto meetLocation;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PostSummaryImageDto> images;
    public static PostDetailResponseDto fromEntity(Post post, List<PostImage> images){
        return PostDetailResponseDto.builder()
                .id(post.getId())
                .nickname(post.getUser().getNickname())
                .title(post.getTitle())
                .content(post.getContent())
                .restaurantName(post.getRestaurantName())
                .categoryCode(post.getCategory().getCategoryCode())
                .deliveryFee(post.getDeliveryFee())
                .minOrderFee(post.getMinOrderFee())
                .meetLocation(PostLocationDto.fromEntity(post))
                .status(post.isStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .images(images.stream().map(PostSummaryImageDto::fromEntity).toList())
                .build();
    }
}
