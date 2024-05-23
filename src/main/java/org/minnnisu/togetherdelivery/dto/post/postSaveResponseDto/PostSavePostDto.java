package org.minnnisu.togetherdelivery.dto.post.postSaveResponseDto;

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
public class PostSavePostDto {
    private Long id;
    private String username;
    private String content;
    private String restaurantName;
    private String categoryCode;
    private int deliveryFee;
    private int minOrderFee;
    private PostLocationDto meetLocation;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<PostSummaryImageDto> postImages;

    public static PostSavePostDto of(Post post, List<PostImage> postImages) {
        return PostSavePostDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .content(post.getContent())
                .restaurantName(post.getRestaurantName())
                .categoryCode(post.getCategory().getCategoryCode())
                .deliveryFee(post.getDeliveryFee())
                .minOrderFee(post.getMinOrderFee())
                .meetLocation(
                        PostLocationDto.fromEntity(post)
                )
                .status(post.isStatus())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .postImages(postImages.stream().map(PostSummaryImageDto::fromEntity).toList())
                .build();
    }
}
