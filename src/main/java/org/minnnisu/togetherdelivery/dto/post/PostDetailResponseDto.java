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
public class PostDetailResponseDto {
    private Long id;
    private String username;
    private String title;
    private String content;
    private String restaurantName;
    private String categoryCode;
    private int deliveryFee;
    private int minOrderFee;
    private String location;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostDetailResponseDto fromEntity(Post post){
        return PostDetailResponseDto.builder()
                .id(post.getId())
                .username(post.getUser().getUsername())
                .title(post.getTitle())
                .content(post.getContent())
                .restaurantName(post.getRestaurantName())
                .categoryCode(post.getCategory().getCategoryCode())
                .deliveryFee(post.getDeliveryFee())
                .minOrderFee(post.getMinOrderFee())
                .location(post.getLocation())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .build();
    }
}
