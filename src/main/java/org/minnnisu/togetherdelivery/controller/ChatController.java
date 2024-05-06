package org.minnnisu.togetherdelivery.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.ChatMessageRequestDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessageSendingOperations sendingOperations;

    @MessageMapping("/chat/message")
    public void chat(ChatMessageRequestDto message) {
//        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
//            message.setMessage(message.getSender()+"님이 입장하였습니다.");
//        }

        sendingOperations.convertAndSend("/topic/chat/room/" + message.getRoomId(), message);
    }

}
