package org.minnnisu.togetherdelivery.dto.chat.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.minnnisu.togetherdelivery.dto.stomp.stompChatMessage.response.StompChatMessageResponseDto;

import java.util.List;

@Getter
@AllArgsConstructor
@Builder
public class ChatMessageListResponseDto {
    private List<StompChatMessageResponseDto> chatMessages;

    public static ChatMessageListResponseDto fromDto(ChatMessageListDto dto) {
        return ChatMessageListResponseDto.builder()
                .chatMessages(dto.getChatMessages())
                .build();
    }
}
