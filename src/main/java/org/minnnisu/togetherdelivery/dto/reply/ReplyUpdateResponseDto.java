package org.minnnisu.togetherdelivery.dto.reply;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Reply;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ReplyUpdateResponseDto {
    private Long replyId;

    private Long commentId;

    private String creator;

    private String content;

    private LocalDateTime updatedAt;

    public static ReplyUpdateResponseDto fromEntity(Reply reply) {
        return ReplyUpdateResponseDto.builder()
                .replyId(reply.getId())
                .commentId(reply.getComment().getId())
                .creator(reply.getUser().getNickname())
                .content(reply.getContent())
                .updatedAt(reply.getUpdatedAt())
                .build();
    }
}
