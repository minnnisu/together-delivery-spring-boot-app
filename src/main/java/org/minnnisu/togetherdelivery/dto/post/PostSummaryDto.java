package org.minnnisu.togetherdelivery.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Post;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostSummaryDto {
    private Long id;
    private String nickname;
    private String content;
    private String restaurantName;
    private String categoryCode;
    private int deliveryFee;
    private int minOrderFee;
    private PostLocationDto meetLocation;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostSummaryDto fromEntity(Post post){
        return PostSummaryDto.builder()
                .id(post.getId())
                .nickname(post.getUser().getNickname())
                .content(post.getContent())
                .restaurantName(post.getRestaurantName())
                .categoryCode(post.getCategory().getCategoryCode())
                .deliveryFee(post.getDeliveryFee())
                .minOrderFee(post.getMinOrderFee())
                .meetLocation(PostLocationDto.fromEntity(post))
                .status(post.isStatus())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
