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
public class Reply {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private Comment comment;

    @ManyToOne
    private User user;

    @Lob
    private String content;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public void update(String content){
        this.content = content;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete(){
        this.user = null;
        this.content = "삭제된 답글입니다.";
        this.deletedAt = LocalDateTime.now();
    }


    public static Reply of(Comment comment, User user, String content) {
        return Reply.builder()
                .comment(comment)
                .user(user)
                .content(content)
                .build();
    }
}
