package org.minnnisu.togetherdelivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.dto.chat.chatMessage.chatMessageInvite.ChatMessageDto;
import org.minnnisu.togetherdelivery.dto.chat.stompChatMessage.StompChatMessageRequestDto;
import org.minnnisu.togetherdelivery.handler.AssignPrincipalHandshakeHandler.StompPrincipal;
import org.minnnisu.togetherdelivery.service.StompChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class StompChatController {
    private final SimpMessageSendingOperations sendingOperations;
    private final StompChatService stompChatService;

    @MessageMapping("/chat/message")
    public void chat(StompChatMessageRequestDto stompChatMessageRequestDto, StompPrincipal stompPrincipal) {

        ChatMessageDto chatMessageDto = stompChatService.sendMessage(stompChatMessageRequestDto, stompPrincipal);

        sendingOperations.convertAndSend(chatMessageDto.getPath(), chatMessageDto.getStompChatMessageResponseDto());
    }
}
