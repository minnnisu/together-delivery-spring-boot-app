package org.minnnisu.togetherdelivery.dto.reply;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyUpdateRequestDto {
    private Long replyId;
    private String content;
}
