package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Getter
@Table
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class ChatMessage {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private ChatRoomPeople chatRoomPeople;

    @Lob
    private String message;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;
}
