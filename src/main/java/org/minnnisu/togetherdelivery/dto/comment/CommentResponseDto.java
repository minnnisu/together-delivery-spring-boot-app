package org.minnnisu.togetherdelivery.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.domain.Comment;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CommentResponseDto {
    private CommentListMetaData metaData;
    private List<CommentListItemDto> comments;

    public static CommentResponseDto of(CommentListMetaData commentListMetaData, List<CommentListItemDto> comments){
        return CommentResponseDto.builder()
                .metaData(commentListMetaData)
                .comments(comments)
                .build();
    }
}
