package org.minnnisu.togetherdelivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.message.ChatMessageListDto;
import org.minnnisu.togetherdelivery.dto.chat.message.ChatMessageListResponseDto;
import org.minnnisu.togetherdelivery.dto.stomp.chatMessage.chatMessageDelete.ChatMessageDeleteDto;
import org.minnnisu.togetherdelivery.dto.stomp.chatMessage.chatMessageDelete.ChatMessageDeleteResponseDto;
import org.minnnisu.togetherdelivery.dto.stomp.stompChatMessage.response.StompChatMessageDeleteResponseDto;
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

    @GetMapping
    public ResponseEntity<ChatMessageListResponseDto> getChatMessageList(@AuthenticationPrincipal User user,
                                                                         @RequestParam(value = "cursor", required = false) Long cursor,
                                                                         @RequestParam(value = "chatRoomId") Long chatRoomId
                                                                         ) {
        ChatMessageListDto chatMessageListDto = chatMessageService.getChatMessageList(user, cursor, chatRoomId);
        return new ResponseEntity<>(ChatMessageListResponseDto.fromDto(chatMessageListDto), HttpStatus.OK);
    }

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
