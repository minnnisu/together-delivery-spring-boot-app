package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomAccess.ChatRoomAccessDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomAccess.ChatRoomAccessResponseDto;
import org.minnnisu.togetherdelivery.dto.stomp.chatMessage.ChatMessageDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomExit.ChatRoomExitRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomExit.ChatRoomExitResponseDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomEnter.*;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomList.ChatRoomListResponseDto;
import org.minnnisu.togetherdelivery.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/room")
public class
ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final SimpMessageSendingOperations sendingOperations;


    @GetMapping()
    public ResponseEntity<ChatRoomListResponseDto> getChatRoomList(@AuthenticationPrincipal User user){
        ChatRoomListResponseDto chatRoomListResponseDto = chatRoomService.getChatRoomList(user);
        return new ResponseEntity<>(chatRoomListResponseDto, HttpStatus.OK);
    }

    @GetMapping("/{chatRoomId}/access")
    public ResponseEntity<ChatRoomAccessResponseDto> accessChatRoom(
            @PathVariable Long chatRoomId,
            @AuthenticationPrincipal User user
    ){
        ChatRoomAccessDto chatRoomAccessDto = chatRoomService.accessChatRoom(chatRoomId, user);
        return new ResponseEntity<>(ChatRoomAccessResponseDto.fromDto(chatRoomAccessDto), HttpStatus.OK);
    }

    @PostMapping("/{chatRoomId}/enter")
    public ResponseEntity<ChatRoomEnterResponseDto> enterChatRoom(
            @PathVariable Long chatRoomId,
            @AuthenticationPrincipal User user
    ){
        ChatRoomEnterDto chatRoomEnterDto = chatRoomService.enterChatRoom(chatRoomId, user);

        ChatMessageDto chatMessageDto = chatRoomEnterDto.getChatMessage();

        sendingOperations.convertAndSend(chatMessageDto.getPath(), chatMessageDto.getStompChatMessageResponseDto());

        return new ResponseEntity<>(ChatRoomEnterResponseDto.fromDto(chatRoomEnterDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/exit")
    public ResponseEntity<ChatRoomExitResponseDto> exitChatRoom(
            @Valid @RequestBody ChatRoomExitRequestDto chatRoomExitRequestDto,
            @AuthenticationPrincipal User user
    ){
        ChatRoomExitResponseDto chatRoomExitResponseDto = chatRoomService.exitChatRoom(chatRoomExitRequestDto, user);
        return new ResponseEntity<>(chatRoomExitResponseDto, HttpStatus.CREATED);
    }
}
