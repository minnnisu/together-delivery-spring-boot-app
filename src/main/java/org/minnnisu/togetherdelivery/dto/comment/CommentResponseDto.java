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
    private List<CommentListItemDto> comments;

    public static CommentResponseDto of(List<CommentListItemDto> comments){
        return CommentResponseDto.builder()
                .comments(comments)
                .build();
    }
}
