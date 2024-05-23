package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.*;
import org.minnnisu.togetherdelivery.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

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
        ChatRoomInviteResponseDto chatRoomInviteResponseDto = chatRoomService.inviteMember(chatRoomInviteRequestDto, user);
        return new ResponseEntity<>(chatRoomInviteResponseDto, HttpStatus.CREATED);
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
