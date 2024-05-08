package org.minnnisu.togetherdelivery.dto.chat;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse.ChatMessageResponseDto;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ChatMessageDto {
    private ChatMessageType chatMessageType;
    private String path;
    private ChatMessageResponseDto chatMessageResponseDto;

    public static ChatMessageDto of(String path, ChatMessageResponseDto chatMessageResponseDto){
        return ChatMessageDto.builder()
                .chatMessageType(chatMessageResponseDto.getType())
                .path(path)
                .chatMessageResponseDto(chatMessageResponseDto)
                .build();
    }
}
