package org.minnnisu.togetherdelivery.dto.chat.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.minnnisu.togetherdelivery.constant.ChatMessageType;
import org.minnnisu.togetherdelivery.constant.ErrorCode;
import org.minnnisu.togetherdelivery.domain.ChatMessage;
import org.minnnisu.togetherdelivery.dto.stomp.stompChatMessage.response.*;
import org.minnnisu.togetherdelivery.exception.CustomErrorException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@Builder
public class ChatMessageListDto {
    private List<StompChatMessageResponseDto> chatMessages;

    public static ChatMessageListDto fromPage(Page<ChatMessage> chatMessagePage) {
        return ChatMessageListDto.builder()
                .chatMessages(chatMessagePage.stream().map(ChatMessageListDto::fromChatMessage
                ).collect(Collectors.toList())).build();
    }

    public static ChatMessageListDto fromList(List<ChatMessage> chatMessageList) {
        return ChatMessageListDto.builder()
                .chatMessages(chatMessageList.stream().map(ChatMessageListDto::fromChatMessage
                ).collect(Collectors.toList())).build();
    }

    public static StompChatMessageResponseDto fromChatMessage(ChatMessage chatMessage) {
        return switch (chatMessage.getChatMessageType()) {
            case TALK -> StompChatMessageTalkResponseDto.fromEntity(chatMessage);
            case OPEN -> StompChatMessageOpenResponseDto.fromEntity(chatMessage);
            case DELETE -> StompChatMessageDeleteResponseDto.fromEntity(chatMessage);
            case LEAVE -> StompChatMessageLeaveResponseDto.fromEntity(chatMessage);
            case ENTER -> StompChatMessageEnterResponseDto.fromEntity(chatMessage);
            default -> throw new CustomErrorException(ErrorCode.InternalServerError);
        };
    }
}
