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
public class ReplySaveResponseDto {
    private Long replyId;

    private Long commentId;

    private String creator;

    private String content;

    private LocalDateTime createdAt;

    public static ReplySaveResponseDto fromEntity(Reply reply) {
        return ReplySaveResponseDto.builder()
                .replyId(reply.getId())
                .commentId(reply.getComment().getId())
                .creator(reply.getUser().getNickname())
                .content(reply.getContent())
                .createdAt(reply.getCreatedAt())
                .build();
    }
}
