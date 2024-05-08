package org.minnnisu.togetherdelivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.dto.chat.ChatMessageDto;
import org.minnnisu.togetherdelivery.dto.chat.ChatMessageRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.chatMessageResponse.ChatMessageResponseDto;
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
    public void chat(ChatMessageRequestDto chatMessageRequestDto, StompPrincipal stompPrincipal) {

        ChatMessageDto chatMessageDto = stompChatService.sendMessage(chatMessageRequestDto, stompPrincipal);

        sendingOperations.convertAndSend(chatMessageDto.getPath(), chatMessageDto.getChatMessageResponseDto());
    }
}
