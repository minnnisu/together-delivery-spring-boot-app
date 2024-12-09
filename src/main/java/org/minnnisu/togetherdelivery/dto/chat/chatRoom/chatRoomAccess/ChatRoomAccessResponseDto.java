package org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomAccess;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ChatRoomAccessResponseDto {
    @JsonProperty("isValid") // JSON 응답 필드 이름을 명시
    private boolean isValid;

    public static ChatRoomAccessResponseDto fromDto(ChatRoomAccessDto dto) {
        return ChatRoomAccessResponseDto.builder()
                .isValid(dto.isValid())
                .build();
    }
}
