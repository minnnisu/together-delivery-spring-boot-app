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
public class ReplyDeleteResponseDto {
    private Long replyId;

    private Long commentId;

    private LocalDateTime deletedAt;

    public static ReplyDeleteResponseDto fromEntity(Reply reply) {
        return ReplyDeleteResponseDto.builder()
                .replyId(reply.getId())
                .commentId(reply.getComment().getId())
                .deletedAt(reply.getDeletedAt())
                .build();
    }
}
