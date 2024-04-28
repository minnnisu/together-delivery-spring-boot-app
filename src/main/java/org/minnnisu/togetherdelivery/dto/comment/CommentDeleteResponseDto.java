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
public class CommentDeleteResponseDto {
    private Long commentId;

    private Long postId;

    private LocalDateTime deletedAt;

    public static CommentDeleteResponseDto fromEntity(Comment comment) {
        return CommentDeleteResponseDto.builder()
                .commentId(comment.getId())
                .postId(comment.getPost().getId())
                .deletedAt(comment.getDeletedAt())
                .build();
    }
}
