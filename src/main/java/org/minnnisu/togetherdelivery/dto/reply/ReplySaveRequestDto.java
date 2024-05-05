package org.minnnisu.togetherdelivery.dto.reply;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplySaveRequestDto {
    private Long commentId;
    private String content;
}
