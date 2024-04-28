package org.minnnisu.togetherdelivery.dto.comment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentSaveRequestDto {
    private Long postId;
    private String content;
}
