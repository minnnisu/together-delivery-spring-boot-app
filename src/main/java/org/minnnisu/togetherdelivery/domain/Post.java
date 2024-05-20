package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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

    private String restaurantName;

    @ManyToOne
    private Category category;

    @OneToOne
    private Location meetLocation;

    private int deliveryFee;

    private int minOrderFee;

    @Lob
    private String content;

    @ColumnDefault("true")
    @Builder.Default()
    private boolean status = true;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public static Post of(Long id,
                          User user,
                          String restaurantName,
                          Category category,
                          Location meetLocation,
                          int deliveryFee,
                          int minOrderFee,
                          String content,
                          boolean status,
                          LocalDateTime createdAt,
                          LocalDateTime updatedAt,
                          LocalDateTime deletedAt
                          ){
        return Post.builder()
                .id(id)
                .user(user)
                .restaurantName(restaurantName)
                .category(category)
                .meetLocation(meetLocation)
                .deliveryFee(deliveryFee)
                .minOrderFee(minOrderFee)
                .content(content)
                .status(status)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .deletedAt(deletedAt)
                .build();
    }

    public static Post of(PostSaveRequestDto postSaveRequestDto, User user, Category category, Location meetLocation) {
        return Post.builder()
                .user(user)
                .content(postSaveRequestDto.getContent())
                .restaurantName(postSaveRequestDto.getRestaurantName())
                .meetLocation(meetLocation)
                .category(category)
                .deliveryFee(postSaveRequestDto.getDeliveryFee())
                .minOrderFee(postSaveRequestDto.getMinOrderFee())
                .build();
    }
}
