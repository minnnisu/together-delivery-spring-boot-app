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
public class PostSaveResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private String restaurantName;
    private String categoryCode;
    private int deliveryFee;
    private int minOrderFee;
    private String location;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PostSummaryImageDto> postImages;

    public static PostSaveResponseDto fromEntity(Post post, List<PostImage> postImages){
        return PostSaveResponseDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .restaurantName(post.getRestaurantName())
                .categoryCode(post.getCategory().getCategoryCode())
                .deliveryFee(post.getDeliveryFee())
                .minOrderFee(post.getMinOrderFee())
                .location(post.getLocation())
                .status(post.isStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .postImages(postImages.stream().map(PostSummaryImageDto::fromEntity).toList())
                .build();
    }
}
