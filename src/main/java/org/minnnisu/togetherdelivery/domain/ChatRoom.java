package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.*;
import lombok.*;
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
public class ChatRoom {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Post post;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public static ChatRoom of(Post post){
        return ChatRoom.builder()
                .post(post)
                .build();
    }
}
