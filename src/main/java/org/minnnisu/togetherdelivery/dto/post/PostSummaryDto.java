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
    private String categoryCode;
    private int deliveryFee;
    private int minOrderFee;
    private boolean status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static PostSummaryDto fromEntity(Post post){
        return PostSummaryDto.builder()
                .id(post.getId())
                .nickname(post.getUser().getNickname())
                .content(post.getContent())
                .categoryCode(post.getCategory().getCategoryCode())
                .deliveryFee(post.getDeliveryFee())
                .minOrderFee(post.getMinOrderFee())
                .status(post.isStatus())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
