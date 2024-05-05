package org.minnnisu.togetherdelivery.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Post;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentSaveResponseDto {
    private Long commentId;

    private Long postId;

    private String creator;

    private String content;

    private LocalDateTime createdAt;

    public static CommentSaveResponseDto fromEntity(Comment comment) {
        return CommentSaveResponseDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .creator(comment.getUser().getNickname())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
