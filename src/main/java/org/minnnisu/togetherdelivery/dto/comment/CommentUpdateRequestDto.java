package org.minnnisu.togetherdelivery.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateRequestDto {
    private Long commentId;
    private String content;
}
