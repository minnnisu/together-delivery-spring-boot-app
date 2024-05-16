package org.minnnisu.togetherdelivery.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
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
public class ChatRoomMember {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private User user;

    @ColumnDefault("false")
    @Builder.Default()
    private boolean isCreator = false;

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime deletedAt;

    public static ChatRoomMember of(ChatRoom chatRoom, User user) {
        return ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .user(user)
                .build();
    }

    public static ChatRoomMember createChatRoomCreator(ChatRoom chatRoom, User user) {
        return ChatRoomMember.builder()
                .chatRoom(chatRoom)
                .user(user)
                .isCreator(true)
                .build();
    }
}
