package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.chatMessage.chatMessageInvite.ChatMessageDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomExit.ChatRoomExitRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomExit.ChatRoomExitResponseDto;
import org.minnnisu.togetherdelivery.dto.chat.chatRoom.chatRoomInvite.*;
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
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    private final SimpMessageSendingOperations sendingOperations;


    @GetMapping()
    public ResponseEntity<ChatRoomListResponseDto> getChatRoomList(@AuthenticationPrincipal User user){
        ChatRoomListResponseDto chatRoomListResponseDto = chatRoomService.getChatRoomList(user);
        return new ResponseEntity<>(chatRoomListResponseDto, HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<ChatRoomInviteResponseDto> inviteMember(
            @Valid @RequestBody ChatRoomInviteRequestDto chatRoomInviteRequestDto,
            @AuthenticationPrincipal User user
    ){
        ChatRoomInviteDto chatRoomInviteDto = chatRoomService.inviteMember(chatRoomInviteRequestDto, user);

        ChatMessageDto chatMessageDto = chatRoomInviteDto.getChatMessage();

        sendingOperations.convertAndSend(chatMessageDto.getPath(), chatMessageDto.getStompChatMessageResponseDto());

        return new ResponseEntity<>(ChatRoomInviteResponseDto.fromDto(chatRoomInviteDto), HttpStatus.CREATED);
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
