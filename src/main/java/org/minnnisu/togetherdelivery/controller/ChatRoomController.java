package org.minnnisu.togetherdelivery.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.minnnisu.togetherdelivery.domain.User;
import org.minnnisu.togetherdelivery.dto.chat.ChatRoomCreateRequestDto;
import org.minnnisu.togetherdelivery.dto.chat.ChatRoomCreateResponseDto;
import org.minnnisu.togetherdelivery.service.ChatRoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat/room")
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping()
    public ResponseEntity<ChatRoomCreateResponseDto> createChatRoom(
            @Valid @RequestBody ChatRoomCreateRequestDto chatRoomCreateRequestDto,
            @AuthenticationPrincipal User user) {
        ChatRoomCreateResponseDto chatRoomCreateResponseDto = chatRoomService.createRoom(chatRoomCreateRequestDto, user);
        return new ResponseEntity<>(chatRoomCreateResponseDto, HttpStatus.CREATED);
    }
}
