package org.minnnisu.togetherdelivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.chatMessage.chatMessageDelete.ChatMessageDeleteDto;
import org.minnnisu.togetherdelivery.dto.chat.chatMessage.chatMessageDelete.ChatMessageDeleteResponseDto;
import org.minnnisu.togetherdelivery.dto.chat.stompChatMessage.StompChatMessageDeleteResponseDto;
import org.minnnisu.togetherdelivery.service.ChatMessageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/message")
public class ChatMessageController {
    private final SimpMessageSendingOperations sendingOperations;

    private final ChatMessageService chatMessageService;

    @DeleteMapping("/{chatMessageId}")
    public ResponseEntity<ChatMessageDeleteResponseDto> deleteMessage(
            @PathVariable Long chatMessageId,
            @AuthenticationPrincipal User user
    ) {
        ChatMessageDeleteDto chatMessageDeleteDto = chatMessageService.deleteChatMessage(chatMessageId, user);
        ChatMessageDeleteResponseDto chatMessageDeleteResponseDto = ChatMessageDeleteResponseDto.fromDto(chatMessageDeleteDto);
        sendingOperations.convertAndSend(
                "/topic/chat/room/" + chatMessageDeleteDto.getChatRoomId(), StompChatMessageDeleteResponseDto.fromEntity(chatMessageDeleteDto)
        );

        return new ResponseEntity<>(chatMessageDeleteResponseDto, HttpStatus.CREATED);
    }
}
