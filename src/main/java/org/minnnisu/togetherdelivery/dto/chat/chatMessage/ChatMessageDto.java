package org.minnnisu.togetherdelivery.dto.chat.chatMessage;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse.stomp.StompChatMessageResponseDto;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    private ChatMessageType chatMessageType;
    private String path;
    private StompChatMessageResponseDto stompChatMessageResponseDto;

    public static ChatMessageDto of(String path, StompChatMessageResponseDto stompChatMessageResponseDto){
        return ChatMessageDto.builder()
                .chatMessageType(stompChatMessageResponseDto.getType())
                .path(path)
                .stompChatMessageResponseDto(stompChatMessageResponseDto)
                .build();
    }
}
