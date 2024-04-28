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
    private List<CommentDto> comments;

    public static CommentResponseDto fromPage(Page<Comment> page){
        return CommentResponseDto.builder()
                .metaData(CommentListMetaData.of(page.getTotalPages(), page.getNumber()))
                .comments(page.map(CommentDto::fromEntity).toList())
                .build();
    }
}
