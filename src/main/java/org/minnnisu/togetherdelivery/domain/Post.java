package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.*;
import lombok.*;
import org.minnnisu.togetherdelivery.dto.post.PostSaveRequestDto;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Post {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private User user;

    private String title;

    @Lob
    private String content;

    private String restaurantName;

    @OneToOne
    private Category category;

    private int deliveryFee;

    private int minOrderFee;

    private String location;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public static Post of(PostSaveRequestDto postSaveRequestDto, User user, Category category) {
        return Post.builder()
                .user(user)
                .title(postSaveRequestDto.getTitle())
                .content(postSaveRequestDto.getContent())
                .restaurantName(postSaveRequestDto.getRestaurantName())
                .category(category)
                .deliveryFee(postSaveRequestDto.getDeliveryFee())
                .minOrderFee(postSaveRequestDto.getMinOrderFee())
                .location(postSaveRequestDto.getLocation())
                .build();
    }
}
