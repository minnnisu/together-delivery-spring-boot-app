package org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomAccess;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ChatRoomAccessDto {
    private boolean isValid;

    public static ChatRoomAccessDto of(boolean isValid) {
        return ChatRoomAccessDto.builder()
                .isValid(isValid)
                .build();
    }
}
