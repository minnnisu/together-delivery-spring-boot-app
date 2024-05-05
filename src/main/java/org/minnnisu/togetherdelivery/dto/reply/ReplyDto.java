package org.minnnisu.togetherdelivery.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Reply;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReplyDto {
    private Long replyId;

    private Long commentId;

    private String creator;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

    public static ReplyDto fromEntity(Reply reply) {
        return ReplyDto.builder()
                .replyId(reply.getId())
                .commentId(reply.getComment().getId())
                .creator(reply.getDeletedAt() == null ? reply.getUser().getNickname() : "알 수 없음")
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .updatedAt(reply.getUpdatedAt())
                .deletedAt(reply.getDeletedAt())
                .build();
    }
}
