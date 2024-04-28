package org.minnnisu.togetherdelivery.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentUpdateResponseDto {
    private Long commentId;

    private Long postId;

    private String creator;

    private String content;

    private LocalDateTime createdAt;

    public static CommentUpdateResponseDto fromEntity(Comment comment) {
        return CommentUpdateResponseDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .creator(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
