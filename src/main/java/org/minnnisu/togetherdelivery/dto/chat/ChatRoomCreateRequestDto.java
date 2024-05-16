package org.minnnisu.togetherdelivery.dto.chat;

import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.validation.ListNotEmpty;

import java.util.List;

@Getter
@Setter
public class ChatRoomCreateRequestDto {
    private Long postId;
}
