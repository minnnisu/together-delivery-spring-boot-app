package org.minnnisu.togetherdelivery.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Reply;
import org.minnnisu.togetherdelivery.dto.reply.ReplyListResponseDto;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentListItemDto {
    private CommentDto comment;
    private ReplyListResponseDto reply;

    public static CommentListItemDto of(Comment comment, Page<Reply> replyPage) {
        return CommentListItemDto.builder()
                .comment(CommentDto.fromEntity(comment))
                .reply(ReplyListResponseDto.fromPage(replyPage))
                .build();
    }
}
