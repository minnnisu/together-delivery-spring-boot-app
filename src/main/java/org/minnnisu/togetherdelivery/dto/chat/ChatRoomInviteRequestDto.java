package org.minnnisu.togetherdelivery.dto.chat;

import lombok.*;
import org.minnnisu.togetherdelivery.validation.ListNotEmpty;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomInviteRequestDto {
    private Long chatRoomId;

    private String invitedMember;
}
