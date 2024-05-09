package org.minnnisu.togetherdelivery.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.minnnisu.togetherdelivery.domain.Reply;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class CommentCreatorResponseDto {
    private List<CreatorDto> comments;
    private List<CreatorDto> replies;

    public static CommentCreatorResponseDto fromEntity(List<Comment> comments, List<Reply> replies){
        return CommentCreatorResponseDto.builder()
                .comments(comments.stream().map(CreatorDto::fromEntity).toList())
                .replies(replies.stream().map(CreatorDto::fromEntity).toList())
                .build();
    }
}
